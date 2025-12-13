package dev.dubhe.lucky.data;

import dev.dubhe.lucky.LuckyBlockMod;
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
        LuckyBlockEffect.Builder
            .effect()
            .type(LuckyBlockEffect.Type.ORDINARY)
            .weight(1)
            .rewards(builder -> builder.addExperience(1))
            .save(saver, "exp_01");
    }
}
