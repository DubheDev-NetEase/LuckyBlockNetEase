package dev.dubhe.lucky.init;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.item.LuckyBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LuckyBlockMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        LuckyBlockMod.MOD_ID
    );
    public static final RegistryObject<CreativeModeTab> LUCKY_BLOCK = CREATIVE_MODE_TABS.register(
        "lucky_block",
        () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.GOLDEN_LUCKY_BLOCK.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.GOLDEN_LUCKY_BLOCK.get());
                output.accept(ModItems.DIAMOND_LUCKY_BLOCK.get());
                output.accept(ModItems.IRON_LUCKY_BLOCK.get());
                output.accept(ModItems.COAL_LUCKY_BLOCK.get());
                output.accept(ModItems.AMETHYST_LUCKY_BLOCK.get());
                output.accept(ModItems.EMERALD_LUCKY_BLOCK.get());
                output.accept(ModItems.COPPER_LUCKY_BLOCK.get());
                output.accept(ModItems.LAPIS_LAZULI_LUCKY_BLOCK.get());
                output.accept(ModItems.NETHERITE_LUCKY_BLOCK.get());
                output.accept(ModItems.QUARTZ_LUCKY_BLOCK.get());
            })
            .build()
    );

    public static final RegistryObject<LuckyBlockItem> GOLDEN_LUCKY_BLOCK = ITEMS.register( // 金幸运方块
        "golden_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.GOLDEN_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> DIAMOND_LUCKY_BLOCK = ITEMS.register( // 钻石幸运方块
        "diamond_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.DIAMOND_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> IRON_LUCKY_BLOCK = ITEMS.register( // 铁幸运方块
        "iron_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.IRON_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> COAL_LUCKY_BLOCK = ITEMS.register( // 煤炭幸运方块
        "coal_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.COAL_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> AMETHYST_LUCKY_BLOCK = ITEMS.register( // 紫水晶幸运方块
        "amethyst_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.AMETHYST_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> EMERALD_LUCKY_BLOCK = ITEMS.register( // 绿宝石幸运方块
        "emerald_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.EMERALD_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> COPPER_LUCKY_BLOCK = ITEMS.register( // 铜幸运方块
        "copper_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.COPPER_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> LAPIS_LAZULI_LUCKY_BLOCK = ITEMS.register( // 青金石幸运方块
        "lapis_lazuli_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.LAPIS_LAZULI_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> NETHERITE_LUCKY_BLOCK = ITEMS.register( // 下界合金幸运方块
        "netherite_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.NETHERITE_LUCKY_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<LuckyBlockItem> QUARTZ_LUCKY_BLOCK = ITEMS.register( // 石英幸运方块
        "quartz_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.QUARTZ_LUCKY_BLOCK.get(), new Item.Properties())
    );
}
