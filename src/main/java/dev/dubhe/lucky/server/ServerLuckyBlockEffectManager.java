package dev.dubhe.lucky.server;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.effect.LuckyBlockEffect;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import dev.dubhe.lucky.init.ModRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ServerLuckyBlockEffectManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private Map<ResourceLocation, LuckyBlockEffectHolder> effects = Map.of();
    private final HolderLookup.Provider registries;

    public ServerLuckyBlockEffectManager(HolderLookup.Provider provider) {
        super(ServerLuckyBlockEffectManager.GSON, Registries.elementsDirPath(ModRegistries.LUCKY_BLOCK_EFFECT));
        this.registries = provider;
    }

    public LuckyBlockEffectHolder random(@NotNull RandomSource randomSource, int luckyLevel, int luckyEffectLevel) {
        int lucky = luckyLevel + 15 * luckyEffectLevel - 20;
        double luck = Math.pow(1.05, lucky);
        List<Normalization> normalizations = getNormalizations(luck);
        double nexted = randomSource.nextDouble();
        for (Normalization normalization : normalizations) {
            if (nexted < normalization.chance) {
                return normalization.holder;
            }
            nexted -= normalization.chance;
        }
        return null;
    }

    public record Normalization(LuckyBlockEffectHolder holder, double chance) {
    }

    private @NotNull List<Normalization> getNormalizations(double luck) {
        List<Normalization> normalizations = new ArrayList<>();
        double sum = 0;
        for (LuckyBlockEffectHolder value : effects.values()) {
            switch (value.value().type()) {
                case GOOD_LUCK -> sum += value.value().weight() * 3 * luck;
                case AWFUL -> sum += value.value().weight() * luck;
                default -> sum += value.value().weight() * 2 * luck;
            }
        }
        for (LuckyBlockEffectHolder value : effects.values()) {
            switch (value.value().type()) {
                case GOOD_LUCK -> normalizations.add(new Normalization(value, value.value().weight() * 3 * luck / sum));
                case AWFUL -> normalizations.add(new Normalization(value, value.value().weight() * luck / sum));
                default -> normalizations.add(new Normalization(value, value.value().weight() * 2 * luck / sum));
            }
        }
        Collections.shuffle(normalizations);
        return normalizations;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    protected void apply(
        @NotNull Map<ResourceLocation, JsonElement> map,
        @NotNull ResourceManager manager,
        @NotNull ProfilerFiller filler
    ) {
        RegistryOps<JsonElement> registryops = this.registries.createSerializationContext(JsonOps.INSTANCE);
        ImmutableMap.Builder<ResourceLocation, LuckyBlockEffectHolder> builder = ImmutableMap.builder();
        map.forEach((location, element) -> {
            try {
                var json = ForgeHooks.readConditionalAdvancement(
                    registryops,
                    element.getAsJsonObject()
                );
                if (json == null) {
                    LuckyBlockMod.LOGGER.debug("Skipping loading lucky block effect {} as its conditions were not met", element);
                    return;
                }
                LuckyBlockEffect effect = LuckyBlockEffect.CODEC.parse(registryops, json).getOrThrow(JsonParseException::new);
                builder.put(location, new LuckyBlockEffectHolder(location, effect));
            } catch (Exception exception) {
                LuckyBlockMod.LOGGER.error("Parsing error loading custom lucky block effect {}: {}", location, exception.getMessage());
            }
        });
        this.effects = builder.buildOrThrow();
    }

    public LuckyBlockEffectHolder get(ResourceLocation location) {
        return this.effects.get(location);
    }

    public Collection<LuckyBlockEffectHolder> getAllEffects() {
        return this.effects.values();
    }
}
