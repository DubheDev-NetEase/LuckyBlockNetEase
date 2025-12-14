package dev.dubhe.lucky.data.effect;

import dev.dubhe.lucky.effect.LuckyBlockEffect;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import dev.dubhe.lucky.init.ModRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class LuckyBlockEffectProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final List<LuckyBlockEffectSubProvider> subProviders;

    public LuckyBlockEffectProvider(
        @NotNull PackOutput output,
        CompletableFuture<HolderLookup.Provider> future,
        List<LuckyBlockEffectSubProvider> providers
    ) {
        this.pathProvider = output.createRegistryElementsPathProvider(ModRegistries.LUCKY_BLOCK_EFFECT);
        this.subProviders = providers;
        this.registries = future;
    }

    public LuckyBlockEffectProvider(
        @NotNull PackOutput output,
        CompletableFuture<HolderLookup.Provider> future,
        ExistingFileHelper existingFileHelper,
        @NotNull List<LuckyBlockEffectGenerator> generators
    ) {
        this(
            output,
            future,
            generators.stream().map(generator -> generator.toSubProvider(existingFileHelper)).toList()
        );
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        return this.registries.thenCompose(provider -> {
            Set<ResourceLocation> set = new HashSet<>();
            List<CompletableFuture<?>> list = new ArrayList<>();
            Consumer<LuckyBlockEffectHolder> consumer = holder -> {
                if (!set.add(holder.id())) {
                    throw new IllegalStateException("Duplicate lucky block effect " + holder.id());
                } else {
                    Path path = this.pathProvider.json(holder.id());
                    list.add(DataProvider.saveStable(output, provider, LuckyBlockEffect.CODEC, holder.value(), path));
                }
            };

            for (LuckyBlockEffectSubProvider subProvider : this.subProviders) {
                subProvider.generate(provider, consumer);
            }

            return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public @NotNull String getName() {
        return "LuckyBlockEffects";
    }

    public interface LuckyBlockEffectGenerator {
        void generate(HolderLookup.Provider registries, Consumer<LuckyBlockEffectHolder> saver, ExistingFileHelper existingFileHelper);

        default LuckyBlockEffectSubProvider toSubProvider(ExistingFileHelper existingFileHelper) {
            return (registries, saver) -> this.generate(registries, saver, existingFileHelper);
        }
    }
}
