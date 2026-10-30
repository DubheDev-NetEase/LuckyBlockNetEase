package dev.dubhe.lucky.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.dubhe.lucky.LuckyBlockMod;
import net.minecraft.resources.Identifier;

import java.util.function.Consumer;

public record LuckyBlockEffect(Type type, int weight, LuckyBlockRewards rewards) {
    public static final MapCodec<LuckyBlockEffect> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Identifier.CODEC.optionalFieldOf("type", Type.ORDINARY.id()).forGetter(effect -> effect.type.id()),
        Codec.INT.fieldOf("weight").forGetter(effect -> effect.weight),
        LuckyBlockRewards.CODEC.fieldOf("rewards").forGetter(effect -> effect.rewards)
    ).apply(instance, LuckyBlockEffect::new));
    public static final Codec<LuckyBlockEffect> CODEC = MAP_CODEC.codec();

    public LuckyBlockEffect(Identifier type, int weight, LuckyBlockRewards rewards) {
        this(Type.get(type), weight, rewards);
    }

    public enum Type {
        GOOD_LUCK("good_luck"), ORDINARY("ordinary"), AWFUL("awful");
        private final Identifier id;

        Type(String name) {
            this.id = LuckyBlockMod.location(name);
        }

        public Identifier id() {
            return id;
        }

        public static Type get(Identifier name) {
            for (Type type : values()) {
                if (type.id().equals(name)) {
                    return type;
                }
            }
            return ORDINARY;
        }
    }

    public static class Builder {
        private Type type;
        private int weight;
        private final LuckyBlockRewards.Builder rewards = new LuckyBlockRewards.Builder();

        public static Builder effect() {
            return new Builder();
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder rewards(Consumer<LuckyBlockRewards.Builder> consumer) {
            consumer.accept(this.rewards);
            return this;
        }

        public LuckyBlockEffect build() {
            return new LuckyBlockEffect(type, weight, rewards.build());
        }

        public LuckyBlockEffectHolder build(Identifier location) {
            return new LuckyBlockEffectHolder(location, this.build());
        }

        public LuckyBlockEffectHolder save(Consumer<LuckyBlockEffectHolder> consumer, String location) {
            return save(consumer, LuckyBlockMod.location(location));
        }

        public LuckyBlockEffectHolder save(Consumer<LuckyBlockEffectHolder> consumer, Identifier location) {
            LuckyBlockEffectHolder holder = this.build(location);
            consumer.accept(holder);
            return holder;
        }
    }
}
