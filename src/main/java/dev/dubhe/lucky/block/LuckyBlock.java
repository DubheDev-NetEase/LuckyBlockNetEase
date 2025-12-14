package dev.dubhe.lucky.block;

import dev.dubhe.lucky.block.entity.LuckyBlockEntity;
import dev.dubhe.lucky.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = "lucky")
public class LuckyBlock extends Block implements EntityBlock {
    private final int defaultLucky;

    public LuckyBlock(int defaultLucky, Properties properties) {
        super(properties);
        this.defaultLucky = defaultLucky;
    }

    public int getDefaultLucky() {
        return defaultLucky;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new LuckyBlockEntity(pos, state, this.defaultLucky);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.@NotNull BreakEvent event) {
        LevelAccessor level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) return;
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(ModBlocks.Tags.LUCKY_BLOCK)) return;
        if (!state.hasBlockEntity()) return;
        LuckyBlockEntity entity = (LuckyBlockEntity) level.getBlockEntity(pos);
        if (entity == null) return;
        MinecraftServer server = serverLevel.getServer();
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        entity.onBreak(server, serverLevel, pos, player);
    }
}
