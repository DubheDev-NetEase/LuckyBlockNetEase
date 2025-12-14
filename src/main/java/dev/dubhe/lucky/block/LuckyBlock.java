package dev.dubhe.lucky.block;

import dev.dubhe.lucky.block.entity.LuckyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
}
