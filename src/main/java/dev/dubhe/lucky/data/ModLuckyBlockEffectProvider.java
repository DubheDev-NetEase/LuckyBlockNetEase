package dev.dubhe.lucky.data;

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
        super(
            packOutput,
            future,
            existingFileHelper,
            List.of(
                ModLuckyBlockEffectProvider::generate
            )
        );
    }

    public static void generate(
        HolderLookup.Provider registries,
        Consumer<LuckyBlockEffectHolder> saver,
        ExistingFileHelper existingFileHelper
    ) {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            LuckyBlockEffect.Builder
                .effect()
                .type(i < 35 ? LuckyBlockEffect.Type.ORDINARY : LuckyBlockEffect.Type.GOOD_LUCK)
                .weight(i)
                .rewards(builder -> builder.addExperience(100 - finalI))
                .save(saver, "exp_0%s".formatted(i));
        }
    }
}
