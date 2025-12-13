package dev.dubhe.lucky.effect;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.commands.CacheableFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record LuckyBlockRewards(
    int experience,
    List<ResourceKey<LootTable>> loot,
    List<ResourceLocation> recipes,
    Optional<ResourceLocation> structures,
    Optional<CacheableFunction> function
) {
    public static final Codec<LuckyBlockRewards> CODEC = RecordCodecBuilder.create(
        p_325186_ -> p_325186_.group(
                Codec.INT.optionalFieldOf("experience", 0).forGetter(LuckyBlockRewards::experience),
                ResourceKey.codec(Registries.LOOT_TABLE).listOf().optionalFieldOf("loot", List.of()).forGetter(LuckyBlockRewards::loot),
                ResourceLocation.CODEC.listOf().optionalFieldOf("recipes", List.of()).forGetter(LuckyBlockRewards::recipes),
                ResourceLocation.CODEC.optionalFieldOf("recipes").forGetter(LuckyBlockRewards::structures),
                CacheableFunction.CODEC.optionalFieldOf("function").forGetter(LuckyBlockRewards::function)
            )
            .apply(p_325186_, LuckyBlockRewards::new)
    );
    public static final LuckyBlockRewards EMPTY = new LuckyBlockRewards(0, List.of(), List.of(), Optional.empty(), Optional.empty());

    @SuppressWarnings("resource")
    public void grant(@NotNull ServerPlayer player) {
        player.giveExperiencePoints(this.experience);
        LootParams lootparams = new LootParams.Builder(player.serverLevel())
            .withParameter(LootContextParams.THIS_ENTITY, player)
            .withParameter(LootContextParams.ORIGIN, player.position())
            .withLuck(player.getLuck())
            .create(LootContextParamSets.ADVANCEMENT_REWARD);
        boolean flag = false;

        for (ResourceKey<LootTable> resourcekey : this.loot) {
            for (ItemStack itemstack : player.server.reloadableRegistries().getLootTable(resourcekey).getRandomItems(lootparams)) {
                if (player.addItem(itemstack)) {
                    player.level()
                        .playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.ITEM_PICKUP,
                            SoundSource.PLAYERS,
                            0.2F,
                            ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                        );
                    flag = true;
                } else {
                    ItemEntity itementity = player.drop(itemstack, false);
                    if (itementity != null) {
                        itementity.setNoPickUpDelay();
                        itementity.setTarget(player.getUUID());
                    }
                }
            }
        }

        if (flag) {
            player.containerMenu.broadcastChanges();
        }

        if (!this.recipes.isEmpty()) {
            player.awardRecipesByKey(this.recipes);
        }

        MinecraftServer minecraftserver = player.server;
        this.function
            .flatMap(function -> function.get(minecraftserver.getFunctions()))
            .ifPresent(commandFunction -> minecraftserver.getFunctions().execute(
                commandFunction,
                player.createCommandSourceStack().withSuppressedOutput().withPermission(2)
            ));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static class Builder {
        private int experience;
        private final ImmutableList.Builder<ResourceKey<LootTable>> loot = ImmutableList.builder();
        private final ImmutableList.Builder<ResourceLocation> recipes = ImmutableList.builder();
        private Optional<ResourceLocation> structures = Optional.empty();
        private Optional<ResourceLocation> function = Optional.empty();

        public static LuckyBlockRewards.Builder experience(int exp) {
            return new LuckyBlockRewards.Builder().addExperience(exp);
        }

        public LuckyBlockRewards.Builder addExperience(int exp) {
            this.experience += exp;
            return this;
        }

        public static LuckyBlockRewards.Builder loot(ResourceKey<LootTable> key) {
            return new LuckyBlockRewards.Builder().addLootTable(key);
        }

        public LuckyBlockRewards.Builder addLootTable(ResourceKey<LootTable> key) {
            this.loot.add(key);
            return this;
        }

        public static LuckyBlockRewards.Builder recipe(ResourceLocation location) {
            return new LuckyBlockRewards.Builder().addRecipe(location);
        }

        public LuckyBlockRewards.Builder addRecipe(ResourceLocation location) {
            this.recipes.add(location);
            return this;
        }

        public static LuckyBlockRewards.Builder function(ResourceLocation location) {
            return new LuckyBlockRewards.Builder().runs(location);
        }

        public LuckyBlockRewards.Builder runs(ResourceLocation location) {
            this.function = Optional.of(location);
            return this;
        }

        public static LuckyBlockRewards.Builder structures(ResourceLocation location) {
            return new LuckyBlockRewards.Builder().placed(location);
        }

        public LuckyBlockRewards.Builder placed(ResourceLocation location) {
            this.structures = Optional.of(location);
            return this;
        }

        public LuckyBlockRewards build() {
            return new LuckyBlockRewards(
                this.experience,
                this.loot.build(),
                this.recipes.build(),
                this.structures,
                this.function.map(CacheableFunction::new)
            );
        }
    }
}
