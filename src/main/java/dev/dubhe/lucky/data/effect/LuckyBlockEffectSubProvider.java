package dev.dubhe.lucky.data.effect;

import dev.dubhe.lucky.effect.LuckyBlockEffect;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface LuckyBlockEffectSubProvider {
    void generate(
        HolderLookup.Provider provider,
        Consumer<LuckyBlockEffectHolder> consumer
    );

    static @NotNull LuckyBlockEffectHolder createPlaceholder(String string) {
        return LuckyBlockEffect.Builder.effect().build(ResourceLocation.parse(string));
    }
}
