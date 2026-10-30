package dev.dubhe.lucky.server;

import com.google.common.collect.ImmutableMap;
import dev.dubhe.lucky.effect.LuckyBlockEffect;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import dev.dubhe.lucky.init.ModRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ServerLuckyBlockEffectManager extends SimpleJsonResourceReloadListener<java.util.Optional<LuckyBlockEffect>> {
    private Map<Identifier, LuckyBlockEffectHolder> effects = Map.of();

    public ServerLuckyBlockEffectManager() {
        super(net.neoforged.neoforge.common.conditions.ConditionalOps.createConditionalCodec(LuckyBlockEffect.CODEC),
            net.minecraft.resources.FileToIdConverter.registry(ModRegistries.LUCKY_BLOCK_EFFECT));
    }

    public LuckyBlockEffectHolder random(RandomSource randomSource, int luckyLevel, int luckyEffectLevel) {
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

    private List<Normalization> getNormalizations(double luck) {
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
    protected void apply(Map<Identifier, java.util.Optional<LuckyBlockEffect>> map, ResourceManager manager, ProfilerFiller filler) {
        ImmutableMap.Builder<Identifier, LuckyBlockEffectHolder> builder = ImmutableMap.builder();
        map.forEach((id, effect) -> effect.ifPresent(value -> builder.put(id, new LuckyBlockEffectHolder(id, value))));
        this.effects = builder.buildOrThrow();
    }

    public LuckyBlockEffectHolder get(Identifier location) {
        return this.effects.get(location);
    }

    public Collection<LuckyBlockEffectHolder> getAllEffects() {
        return this.effects.values();
    }
}
