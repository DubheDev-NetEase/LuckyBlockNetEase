package dev.dubhe.lucky.data;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider extends BlockTagsProvider {
    public ModTagProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, LuckyBlockMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider lookupProvider) {
        tag(ModBlocks.Tags.LUCKY_BLOCK).add(
            ModBlocks.AMETHYST_LUCKY_BLOCK.get(),
            ModBlocks.COAL_LUCKY_BLOCK.get(),
            ModBlocks.COPPER_LUCKY_BLOCK.get(),
            ModBlocks.DIAMOND_LUCKY_BLOCK.get(),
            ModBlocks.EMERALD_LUCKY_BLOCK.get(),
            ModBlocks.GOLDEN_LUCKY_BLOCK.get(),
            ModBlocks.IRON_LUCKY_BLOCK.get(),
            ModBlocks.LAPIS_LAZULI_LUCKY_BLOCK.get(),
            ModBlocks.NETHERITE_LUCKY_BLOCK.get(),
            ModBlocks.QUARTZ_LUCKY_BLOCK.get()
        );

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .addTag(ModBlocks.Tags.LUCKY_BLOCK);
    }
}
