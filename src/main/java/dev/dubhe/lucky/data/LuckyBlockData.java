package dev.dubhe.lucky.data;

import dev.dubhe.lucky.LuckyBlockMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = LuckyBlockMod.MOD_ID)
public class LuckyBlockData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ResourceManager existingFileHelper = event.getResourceManager(net.minecraft.server.packs.PackType.SERVER_DATA);
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(true, new ModLootTableProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new ModTagProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModLuckyBlockEffectProvider(packOutput, existingFileHelper, lookupProvider));
    }
}
