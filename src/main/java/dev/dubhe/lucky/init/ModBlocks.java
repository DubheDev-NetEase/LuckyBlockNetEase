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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LuckyBlockMod.MOD_ID);

    public static final RegistryObject<LuckyBlock> GOLDEN_LUCKY_BLOCK = BLOCKS.register( // 金幸运方块
        "golden_lucky_block",
        () -> new LuckyBlock(
            10,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.GOLD_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> DIAMOND_LUCKY_BLOCK = BLOCKS.register( // 钻石幸运方块
        "diamond_lucky_block",
        () -> new LuckyBlock(
            100,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.DIAMOND_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> IRON_LUCKY_BLOCK = BLOCKS.register( // 铁幸运方块
        "iron_lucky_block",
        () -> new LuckyBlock(
            5,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.IRON_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> COAL_LUCKY_BLOCK = BLOCKS.register( // 煤炭幸运方块
        "coal_lucky_block",
        () -> new LuckyBlock(
            1,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.COAL_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> AMETHYST_LUCKY_BLOCK = BLOCKS.register( // 紫水晶幸运方块
        "amethyst_lucky_block",
        () -> new LuckyBlock(
            8,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.AMETHYST_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> EMERALD_LUCKY_BLOCK = BLOCKS.register( // 绿宝石幸运方块
        "emerald_lucky_block",
        () -> new LuckyBlock(
            30,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.EMERALD_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> COPPER_LUCKY_BLOCK = BLOCKS.register( // 铜幸运方块
        "copper_lucky_block",
        () -> new LuckyBlock(
            2,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.COPPER_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> LAPIS_LAZULI_LUCKY_BLOCK = BLOCKS.register( // 青金石幸运方块
        "lapis_lazuli_lucky_block",
        () -> new LuckyBlock(
            20,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.LAPIS_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> NETHERITE_LUCKY_BLOCK = BLOCKS.register( // 下界合金幸运方块
        "netherite_lucky_block",
        () -> new LuckyBlock(
            150,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.NETHERITE_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final RegistryObject<LuckyBlock> QUARTZ_LUCKY_BLOCK = BLOCKS.register( // 石英幸运方块
        "quartz_lucky_block",
        () -> new LuckyBlock(
            7,
            BlockBehaviour.Properties
                .ofFullCopy(Blocks.QUARTZ_BLOCK)
                .lightLevel((state) -> 4)
                .noOcclusion()
        )
    );

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
        ForgeRegistries.BLOCK_ENTITY_TYPES,
        LuckyBlockMod.MOD_ID
    );

    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<LuckyBlockEntity>> LUCKY_BLOCK = BLOCK_ENTITY_TYPES.register(
        "lucky_block",
        () -> BlockEntityType.Builder
            .of(
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
            .build(null)
    );

    public static class Tags {
        public static final TagKey<Block> LUCKY_BLOCK = TagKey.create(
            Registries.BLOCK,
            LuckyBlockMod.location("lucky_block")
        );
    }
}
