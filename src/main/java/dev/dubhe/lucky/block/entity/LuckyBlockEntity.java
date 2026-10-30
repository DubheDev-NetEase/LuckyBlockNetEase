package dev.dubhe.lucky.block.entity;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import dev.dubhe.lucky.init.ModBlocks;
import dev.dubhe.lucky.server.ServerLuckyBlockEffectManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jspecify.annotations.Nullable;

public class LuckyBlockEntity extends BlockEntity {
    private int lucky;

    public LuckyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LUCKY_BLOCK.get(), pos, state);
    }

    public LuckyBlockEntity(BlockPos pos, BlockState state, int lucky) {
        super(ModBlocks.LUCKY_BLOCK.get(), pos, state);
        this.lucky = lucky;
    }

    @Override
    protected void saveAdditional(ValueOutput tag) {
        super.saveAdditional(tag);
        tag.putInt("lucky", this.lucky);
    }

    @Override
    protected void loadAdditional(ValueInput tag) {
        super.loadAdditional(tag);
        this.lucky = tag.getIntOr("lucky", 0);
    }

    public void setLucky(int lucky) {
        this.lucky = lucky;
    }

    public void onBreak(MinecraftServer server, ServerLevel level, BlockPos pos, @Nullable ServerPlayer player) {
        ServerLuckyBlockEffectManager manager = LuckyBlockMod.luckyBlockEffectManager;
        if (manager == null) {
            return;
        }
        int luckyEffectLevel = 0;
        RandomSource randomSource = level.random;
        CommandSourceStack source = server.createCommandSourceStack();
        if (player != null) {
            randomSource = player.getRandom();
            MobEffectInstance instance = player.getEffect(MobEffects.LUCK);
            if (instance != null) {
            luckyEffectLevel = instance.getAmplifier();
        }
            source = player.createCommandSourceStack();
        }
        LuckyBlockEffectHolder holder = manager.random(randomSource, this.lucky, luckyEffectLevel);
        holder.value().rewards().grant(server, level, source, pos);
    }
}
