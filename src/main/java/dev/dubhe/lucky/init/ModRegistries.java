package dev.dubhe.lucky.init;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.effect.LuckyBlockEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

public class ModRegistries {
    public static final ResourceKey<Registry<LuckyBlockEffect>> LUCKY_BLOCK_EFFECT = ModRegistries.createRegistryKey("lucky_block_effect");

    @SuppressWarnings("SameParameterValue")
    private static <T> @NotNull ResourceKey<Registry<T>> createRegistryKey(String path) {
        return ResourceKey.createRegistryKey(LuckyBlockMod.location(path));
    }
}
