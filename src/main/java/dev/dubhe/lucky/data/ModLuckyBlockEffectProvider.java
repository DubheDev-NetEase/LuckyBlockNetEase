package dev.dubhe.lucky.data;

import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.data.effect.LuckyBlockEffectProvider;
import dev.dubhe.lucky.effect.LuckyBlockEffect;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModLuckyBlockEffectProvider extends LuckyBlockEffectProvider {
    public ModLuckyBlockEffectProvider(
        PackOutput packOutput,
        @NotNull ExistingFileHelper existingFileHelper,
        CompletableFuture<HolderLookup.Provider> future
    ) {
        super(packOutput, future, existingFileHelper, List.of(ModLuckyBlockEffectProvider::generate));
    }

    public static void generate(
        HolderLookup.Provider registries,
        Consumer<LuckyBlockEffectHolder> saver,
        ExistingFileHelper existingFileHelper
    ) {
        GenerateHelper helper = new GenerateHelper(registries, saver, existingFileHelper);
        for (int i = 50; i < 100; i++) {
            helper.exp(i, i / 10);
        }
        helper.func("large_cube/large_amethyst_cube", 100);
        helper.func("large_cube/large_coal_cube", 300);
        helper.func("large_cube/large_copper_cube", 180);
        helper.func("large_cube/large_diamond_cube", 20);
        helper.func("large_cube/large_emerald_cube", 25);
        helper.func("large_cube/large_gold_cube", 60);
        helper.func("large_cube/large_iron_cube", 80);
        helper.func("large_cube/large_lapis_cube", 90);
        helper.func("large_cube/large_netherite_cube", 1);
        helper.func("large_cube/large_quartz_cube", 120);
        helper.awfulFunc("filling_anvil", 120);
        helper.awfulFunc("lightning_bolt", 75);
        helper.awfulFunc("mob/zombie", 100);
        helper.awfulFunc("mob/zombie_villager", 100);
        helper.awfulFunc("mob/piglin", 80);
        helper.awfulFunc("mob/pillager", 75);
        helper.awfulFunc("mob/creeper", 60);
        helper.awfulFunc("mob/blaze", 50);
        helper.awfulFunc("mob/piglin_brute", 40);
        helper.awfulFunc("mob/evoker", 35);
    }

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    public static class GenerateHelper {
        private final HolderLookup.Provider registries;
        private final Consumer<LuckyBlockEffectHolder> saver;
        private final ExistingFileHelper existingFileHelper;

        public GenerateHelper(
            HolderLookup.Provider registries,
            Consumer<LuckyBlockEffectHolder> saver,
            ExistingFileHelper existingFileHelper
        ) {
            this.registries = registries;
            this.saver = saver;
            this.existingFileHelper = existingFileHelper;
        }

        public void exp(int exp, int weight) {
            LuckyBlockEffect.Builder.effect()
                .type(exp < 75 ? LuckyBlockEffect.Type.ORDINARY : LuckyBlockEffect.Type.GOOD_LUCK)
                .weight(weight)
                .rewards(builder -> builder.addExperience(exp))
                .save(this.saver, "exp/exp_0%s".formatted(exp));
        }

        public void func(String name, int weight) {
            LuckyBlockEffect.Builder.effect()
                .type(LuckyBlockEffect.Type.GOOD_LUCK)
                .weight(weight)
                .rewards(builder -> builder.runs(LuckyBlockMod.location(name)))
                .save(saver, name);
        }

        public void awfulFunc(String name, int weight) {
            LuckyBlockEffect.Builder.effect()
                .type(LuckyBlockEffect.Type.AWFUL)
                .weight(weight)
                .rewards(builder -> builder.runs(LuckyBlockMod.location(name)))
                .save(saver, name);
        }
    }
}
