package dev.dubhe.lucky.init;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.item.LuckyBlockItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, LuckyBlockMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        LuckyBlockMod.MOD_ID
    );
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LUCKY_BLOCK = CREATIVE_MODE_TABS.register(
        "lucky_block",
        () -> CreativeModeTab.builder()
            .title(net.minecraft.network.chat.Component.translatable("itemGroup.lucky_block"))
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

    public static final DeferredHolder<Item, LuckyBlockItem> GOLDEN_LUCKY_BLOCK = ITEMS.register(
        "golden_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.GOLDEN_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("golden_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> DIAMOND_LUCKY_BLOCK = ITEMS.register(
        "diamond_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.DIAMOND_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("diamond_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> IRON_LUCKY_BLOCK = ITEMS.register(
        "iron_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.IRON_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("iron_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> COAL_LUCKY_BLOCK = ITEMS.register(
        "coal_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.COAL_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("coal_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> AMETHYST_LUCKY_BLOCK = ITEMS.register(
        "amethyst_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.AMETHYST_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("amethyst_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> EMERALD_LUCKY_BLOCK = ITEMS.register(
        "emerald_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.EMERALD_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("emerald_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> COPPER_LUCKY_BLOCK = ITEMS.register(
        "copper_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.COPPER_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("copper_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> LAPIS_LAZULI_LUCKY_BLOCK = ITEMS.register(
        "lapis_lazuli_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.LAPIS_LAZULI_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("lapis_lazuli_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> NETHERITE_LUCKY_BLOCK = ITEMS.register(
        "netherite_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.NETHERITE_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("netherite_lucky_block"))))
    );

    public static final DeferredHolder<Item, LuckyBlockItem> QUARTZ_LUCKY_BLOCK = ITEMS.register(
        "quartz_lucky_block",
        () -> new LuckyBlockItem(ModBlocks.QUARTZ_LUCKY_BLOCK.get(), new Item.Properties().setId(net.minecraft.resources.ResourceKey.create(Registries.ITEM, LuckyBlockMod.location("quartz_lucky_block"))))
    );

    public static class DataComponents {
        public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(
            Registries.DATA_COMPONENT_TYPE,
            LuckyBlockMod.MOD_ID
        );
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> LUCKY = DATA_COMPONENT_TYPES.register(
            "lucky",
            () -> DataComponentType.<Integer>builder()
                .persistent(ExtraCodecs.NON_NEGATIVE_INT)
                .networkSynchronized(ByteBufCodecs.VAR_INT)
                .build()
        );
    }
}
