package dev.dubhe.lucky.item;

import dev.dubhe.lucky.block.LuckyBlock;
import dev.dubhe.lucky.block.entity.LuckyBlockEntity;
import dev.dubhe.lucky.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LuckyBlockItem extends BlockItem {
    public LuckyBlockItem(LuckyBlock luckyBlock, @NotNull Properties properties) {
        super(luckyBlock, properties.component(ModItems.DataComponents.LUCKY.get(), luckyBlock.getDefaultLucky()));
    }

    @Override
    protected boolean updateCustomBlockEntityTag(
        @NotNull BlockPos pos,
        @NotNull Level level,
        @Nullable Player player,
        @NotNull ItemStack stack,
        @NotNull BlockState state
    ) {
        boolean updated = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        if (!stack.has(ModItems.DataComponents.LUCKY.get())) return updated;
        @SuppressWarnings("DataFlowIssue")
        int lucky = stack.get(ModItems.DataComponents.LUCKY.get());
        LuckyBlockEntity entity = (LuckyBlockEntity) level.getBlockEntity(pos);
        if (entity == null) return updated;
        entity.setLucky(lucky);
        return updated;
    }
}
