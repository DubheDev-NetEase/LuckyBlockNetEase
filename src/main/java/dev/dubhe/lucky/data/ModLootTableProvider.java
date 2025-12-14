package dev.dubhe.lucky.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> provider
    ) {
        super(
            output,
            Set.of(),
            List.of(
                new SubProviderEntry(ModLootSubProvider::new, LootContextParamSets.CHEST)
            ),
            provider
        );
    }

    public static class ModLootSubProvider implements LootTableSubProvider {
        @SuppressWarnings({"unused", "FieldCanBeLocal"})
        private final HolderLookup.Provider provider;

        public ModLootSubProvider(HolderLookup.Provider provider) {
            this.provider = provider;
        }

        @Override
        public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {

        }
    }
}
