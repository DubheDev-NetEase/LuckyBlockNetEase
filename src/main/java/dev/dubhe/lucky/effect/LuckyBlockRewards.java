package dev.dubhe.lucky.effect;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.commands.CacheableFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
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
    List<String> commands,
    Optional<ResourceLocation> structure,
    Optional<CacheableFunction> function
) {
    public static final Codec<LuckyBlockRewards> CODEC = RecordCodecBuilder.create(
        p_325186_ -> p_325186_.group(
                Codec.INT.optionalFieldOf("experience", 0).forGetter(LuckyBlockRewards::experience),
                ResourceKey.codec(Registries.LOOT_TABLE).listOf().optionalFieldOf("loot", List.of()).forGetter(LuckyBlockRewards::loot),
                Codec.STRING.listOf().optionalFieldOf("commands", List.of()).forGetter(LuckyBlockRewards::commands),
                ResourceLocation.CODEC.optionalFieldOf("structure").forGetter(LuckyBlockRewards::structure),
                CacheableFunction.CODEC.optionalFieldOf("function").forGetter(LuckyBlockRewards::function)
            )
            .apply(p_325186_, LuckyBlockRewards::new)
    );

    public void grant(MinecraftServer server, ServerLevel level, @NotNull CommandSourceStack stack, BlockPos pos) {
        this.structure.ifPresent(structure -> {
            StructureTemplateManager manager = level.getStructureManager();
            StructurePlaceSettings settings = new StructurePlaceSettings();
            manager.get(structure).ifPresent(template -> template.placeInWorld(
                level,
                pos,
                pos,
                settings,
                StructureBlockEntity.createRandom(0),
                2
            ));
        });
        this.function
            .flatMap(function -> function.get(server.getFunctions()))
            .ifPresent(commandFunction -> server.getFunctions().execute(
                commandFunction,
                stack.withSuppressedOutput()
                    .withPermission(2)
            ));
        ServerPlayer player = stack.getPlayer();
        if (player == null) return;
        int exp = this.experience;
        while (exp > 0) {
            int spa = Math.min(exp, 7);
            exp -= spa;
            ExperienceOrb orb = new ExperienceOrb(level, pos.getX(), pos.getY(), pos.getZ(), spa);
            level.addFreshEntity(orb);
        }
        LootParams lootparams = new LootParams.Builder(player.serverLevel())
            .withParameter(LootContextParams.THIS_ENTITY, player)
            .withParameter(LootContextParams.ORIGIN, pos.getCenter())
            .withLuck(player.getLuck())
            .create(LootContextParamSets.ADVANCEMENT_REWARD);

        for (ResourceKey<LootTable> resourcekey : this.loot) {
            for (ItemStack itemstack : server.reloadableRegistries().getLootTable(resourcekey).getRandomItems(lootparams)) {
                ItemEntity itementity = new ItemEntity(level, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, itemstack);
                float f = level.random.nextFloat() * 0.5F;
                float f1 = level.random.nextFloat() * (float) (Math.PI * 2);
                itementity.setDeltaMovement(-Mth.sin(f1) * f, 0.2F, Mth.cos(f1) * f);
                level.addFreshEntity(itementity);
            }
        }

        if (!this.commands.isEmpty()) {
            for (String command : commands) {
                server.getCommands().performPrefixedCommand(stack, command);
            }
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static class Builder {
        private int experience;
        private final ImmutableList.Builder<ResourceKey<LootTable>> loot = ImmutableList.builder();
        private final ImmutableList.Builder<String> commands = ImmutableList.builder();
        private Optional<ResourceLocation> structure = Optional.empty();
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

        public static LuckyBlockRewards.Builder command(String command) {
            return new LuckyBlockRewards.Builder().addCommand(command);
        }

        public LuckyBlockRewards.Builder addCommand(String command) {
            this.commands.add(command);
            return this;
        }

        public static LuckyBlockRewards.Builder function(ResourceLocation location) {
            return new LuckyBlockRewards.Builder().runs(location);
        }

        public LuckyBlockRewards.Builder runs(ResourceLocation location) {
            this.function = Optional.of(location);
            return this;
        }

        public static LuckyBlockRewards.Builder structure(ResourceLocation location) {
            return new LuckyBlockRewards.Builder().placed(location);
        }

        public LuckyBlockRewards.Builder placed(ResourceLocation location) {
            this.structure = Optional.of(location);
            return this;
        }

        public LuckyBlockRewards build() {
            return new LuckyBlockRewards(
                this.experience,
                this.loot.build(),
                this.commands.build(),
                this.structure,
                this.function.map(CacheableFunction::new)
            );
        }
    }
}
