package dev.dubhe.lucky.block.entity;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import dev.dubhe.lucky.init.ModBlocks;
import dev.dubhe.lucky.server.ServerLuckyBlockEffectManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("lucky", this.lucky);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        this.lucky = tag.getInt("lucky");
    }

    public void setLucky(int lucky) {
        this.lucky += lucky;
    }

    public void onBreak(MinecraftServer server, @NotNull ServerLevel level, @NotNull BlockPos pos, @Nullable ServerPlayer player) {
        ServerLuckyBlockEffectManager manager = LuckyBlockMod.luckyBlockEffectManager;
        if (manager == null) return;
        int luckyEffectLevel = 0;
        RandomSource randomSource = level.random;
        CommandSourceStack source = server.createCommandSourceStack();
        if (player != null) {
            randomSource = player.getRandom();
            MobEffectInstance instance = player.getEffect(MobEffects.LUCK);
            if (instance != null) luckyEffectLevel = instance.getAmplifier();
            source = player.createCommandSourceStack();
        }
        LuckyBlockEffectHolder holder = manager.random(randomSource, this.lucky, luckyEffectLevel);
        holder.value().rewards().grant(server, level, source, pos);
    }
}
