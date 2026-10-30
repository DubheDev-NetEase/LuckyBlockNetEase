package dev.dubhe.lucky.init;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.effect.LuckyBlockEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ModRegistries {
    public static final ResourceKey<Registry<LuckyBlockEffect>> LUCKY_BLOCK_EFFECT = ModRegistries.createRegistryKey("lucky_block_effect");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String path) {
        return ResourceKey.createRegistryKey(LuckyBlockMod.location(path));
    }
}
