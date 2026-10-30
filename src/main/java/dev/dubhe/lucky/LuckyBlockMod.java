package dev.dubhe.lucky;

import com.mojang.logging.LogUtils;
import dev.dubhe.lucky.block.entity.LuckyBlockEntity;
import dev.dubhe.lucky.init.ModBlocks;
import dev.dubhe.lucky.init.ModItems;
import dev.dubhe.lucky.server.ServerLuckyBlockEffectManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import org.slf4j.Logger;

@Mod(LuckyBlockMod.MOD_ID)
public class LuckyBlockMod {
    public static final String MOD_ID = "lucky_block";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ServerLuckyBlockEffectManager luckyBlockEffectManager = null;

    public LuckyBlockMod(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModItems.CREATIVE_MODE_TABS.register(modEventBus);
        ModItems.DataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        NeoForge.EVENT_BUS.addListener(this::onResourceReload);
        NeoForge.EVENT_BUS.addListener(this::onServerStopped);
        NeoForge.EVENT_BUS.addListener(this::onBlockBreak);
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public void onResourceReload(AddServerReloadListenersEvent event) {
        if (LuckyBlockMod.luckyBlockEffectManager == null) {
            LuckyBlockMod.luckyBlockEffectManager = new ServerLuckyBlockEffectManager();
        }
        event.addListener(location("lucky_block_effects"), luckyBlockEffectManager);
    }

    public void onServerStopped(ServerStoppedEvent event) {
        LuckyBlockMod.luckyBlockEffectManager = null;
    }

    public void onBlockBreak(BlockEvent.BreakEvent event) {
        LevelAccessor level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(ModBlocks.Tags.LUCKY_BLOCK)) {
            return;
        }
        if (!state.hasBlockEntity()) {
            return;
        }
        LuckyBlockEntity entity = (LuckyBlockEntity) level.getBlockEntity(pos);
        if (entity == null) {
            return;
        }
        MinecraftServer server = serverLevel.getServer();
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        entity.onBreak(server, serverLevel, pos, player);
    }
}
