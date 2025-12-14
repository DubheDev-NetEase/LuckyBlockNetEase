package dev.dubhe.lucky.data;

import dev.dubhe.lucky.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AMETHYST_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.AMETHYST_SHARD)
            .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COAL_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.COAL)
            .unlockedBy(getHasName(Items.COAL), has(Items.COAL))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.COPPER_INGOT)
            .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DIAMOND_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.DIAMOND)
            .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EMERALD_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.EMERALD)
            .unlockedBy(getHasName(Items.EMERALD), has(Items.EMERALD))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GOLDEN_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.GOLD_INGOT)
            .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.IRON_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.IRON_INGOT)
            .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LAPIS_LAZULI_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.LAPIS_LAZULI)
            .unlockedBy(getHasName(Items.LAPIS_LAZULI), has(Items.LAPIS_LAZULI))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.NETHERITE_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.NETHERITE_INGOT)
            .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.QUARTZ_LUCKY_BLOCK.get())
            .pattern("###")
            .pattern("#*#")
            .pattern("###")
            .define('#', ItemTags.PLANKS)
            .define('*', Items.QUARTZ)
            .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
            .save(output);
    }
}
