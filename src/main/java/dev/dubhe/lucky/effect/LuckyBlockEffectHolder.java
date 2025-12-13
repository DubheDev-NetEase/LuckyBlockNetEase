package dev.dubhe.lucky.effect;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record LuckyBlockEffectHolder(ResourceLocation id, LuckyBlockEffect value) {
    @Override
    public boolean equals(Object object) {
        return this == object || (object instanceof LuckyBlockEffectHolder holder && this.id().equals(holder.id()));
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public @NotNull String toString() {
        return this.id.toString();
    }
}
