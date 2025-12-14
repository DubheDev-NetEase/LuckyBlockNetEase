package dev.dubhe.lucky.data;

import dev.dubhe.lucky.LuckyBlockMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = LuckyBlockMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuckyBlockData {
    @SubscribeEvent
    public static void onGatherData(@NotNull GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new ModLootTableProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModLuckyBlockEffectProvider(packOutput, existingFileHelper, lookupProvider));
    }
}
