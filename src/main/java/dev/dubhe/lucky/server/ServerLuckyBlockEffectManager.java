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
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public class ServerLuckyBlockEffectManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();
    private Map<ResourceLocation, LuckyBlockEffectHolder> effects = Map.of();
    private final HolderLookup.Provider registries;

    public ServerLuckyBlockEffectManager(HolderLookup.Provider provider) {
        super(ServerLuckyBlockEffectManager.GSON, Registries.elementsDirPath(ModRegistries.LUCKY_BLOCK_EFFECT));
        this.registries = provider;
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
