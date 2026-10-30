package dev.dubhe.lucky.init;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.block.LuckyBlock;
import dev.dubhe.lucky.block.entity.LuckyBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, LuckyBlockMod.MOD_ID);

    public static final DeferredHolder<Block, LuckyBlock> GOLDEN_LUCKY_BLOCK = BLOCKS.register(
        "golden_lucky_block",
        () -> new LuckyBlock(
            10,
            BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("golden_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> DIAMOND_LUCKY_BLOCK = BLOCKS.register(
        "diamond_lucky_block",
        () -> new LuckyBlock(
            100,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("diamond_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> IRON_LUCKY_BLOCK = BLOCKS.register(
        "iron_lucky_block",
        () -> new LuckyBlock(
            5,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("iron_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> COAL_LUCKY_BLOCK = BLOCKS.register(
        "coal_lucky_block",
        () -> new LuckyBlock(
            1,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("coal_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> AMETHYST_LUCKY_BLOCK = BLOCKS.register(
        "amethyst_lucky_block",
        () -> new LuckyBlock(
            8,
            BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("amethyst_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> EMERALD_LUCKY_BLOCK = BLOCKS.register(
        "emerald_lucky_block",
        () -> new LuckyBlock(
            30,
            BlockBehaviour.Properties.ofFullCopy(Blocks.EMERALD_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("emerald_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> COPPER_LUCKY_BLOCK = BLOCKS.register(
        "copper_lucky_block",
        () -> new LuckyBlock(
            2,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("copper_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> LAPIS_LAZULI_LUCKY_BLOCK = BLOCKS.register(
        "lapis_lazuli_lucky_block",
        () -> new LuckyBlock(
            20,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LAPIS_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("lapis_lazuli_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> NETHERITE_LUCKY_BLOCK = BLOCKS.register(
        "netherite_lucky_block",
        () -> new LuckyBlock(
            150,
            BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("netherite_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredHolder<Block, LuckyBlock> QUARTZ_LUCKY_BLOCK = BLOCKS.register(
        "quartz_lucky_block",
        () -> new LuckyBlock(
            7,
            BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
                .setId(net.minecraft.resources.ResourceKey.create(Registries.BLOCK, LuckyBlockMod.location("quartz_lucky_block")))
                .lightLevel((state) -> 4)
                .noOcclusion()
                .strength(0.8F)
        )
    );

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
        Registries.BLOCK_ENTITY_TYPE,
        LuckyBlockMod.MOD_ID
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LuckyBlockEntity>> LUCKY_BLOCK = BLOCK_ENTITY_TYPES.register(
        "lucky_block",
        () -> new BlockEntityType<>(
                LuckyBlockEntity::new,
                GOLDEN_LUCKY_BLOCK.get(),
                DIAMOND_LUCKY_BLOCK.get(),
                IRON_LUCKY_BLOCK.get(),
                COAL_LUCKY_BLOCK.get(),
                AMETHYST_LUCKY_BLOCK.get(),
                EMERALD_LUCKY_BLOCK.get(),
                COPPER_LUCKY_BLOCK.get(),
                LAPIS_LAZULI_LUCKY_BLOCK.get(),
                NETHERITE_LUCKY_BLOCK.get(),
                QUARTZ_LUCKY_BLOCK.get()
            )
    );

    public static class Tags {
        public static final TagKey<Block> LUCKY_BLOCK = TagKey.create(
            Registries.BLOCK,
            LuckyBlockMod.location("lucky_block")
        );
    }
}
