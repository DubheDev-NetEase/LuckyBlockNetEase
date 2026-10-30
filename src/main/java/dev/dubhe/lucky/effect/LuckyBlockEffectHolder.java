package dev.dubhe.lucky.effect;

import net.minecraft.resources.Identifier;

public record LuckyBlockEffectHolder(Identifier id, LuckyBlockEffect value) {
    @Override
    public boolean equals(Object object) {
        return this == object || (object instanceof LuckyBlockEffectHolder holder && this.id().equals(holder.id()));
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
