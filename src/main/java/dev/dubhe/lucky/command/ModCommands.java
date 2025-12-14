package dev.dubhe.lucky.command;

import com.google.common.base.Supplier;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import dev.dubhe.lucky.server.ServerLuckyBlockEffectManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = LuckyBlockMod.MOD_ID)
public class ModCommands {
    @SubscribeEvent
    public static void onCommandRegister(@NotNull RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("lb")
                .requires(source -> source.hasPermission(Commands.LEVEL_ADMINS))
                .then(
                    Commands.literal("effects")
                        .executes(ModCommands::effects)
                ).then(
                    Commands.literal("test")
                        .executes(ModCommands::test)
                        .then(
                            Commands.argument("lucky", IntegerArgumentType.integer(0, 1000))
                                .executes(ModCommands::test)
                                .then(
                                    Commands.argument("pos", BlockPosArgument.blockPos())
                                        .executes(ModCommands::test)
                                )
                        )
                )
        );
    }

    public static int effects(CommandContext<CommandSourceStack> context) {
        ServerLuckyBlockEffectManager manager = LuckyBlockMod.luckyBlockEffectManager;
        if (manager == null) return 0;
        Collection<LuckyBlockEffectHolder> holders = manager.getAllEffects();
        int size = holders.size();
        CommandSourceStack source = context.getSource();
        Supplier<Component> supplier = () -> Component.literal("==== all %s effects ====".formatted(size));
        source.sendSuccess(supplier, false);
        for (LuckyBlockEffectHolder effect : holders) {
            source.sendSuccess(() -> Component.literal("- %s".formatted(effect.id())), false);
        }
        source.sendSuccess(supplier, false);
        return size;
    }

    public static int test(CommandContext<CommandSourceStack> context) {
        ServerLuckyBlockEffectManager manager = LuckyBlockMod.luckyBlockEffectManager;
        if (manager == null) return 0;
        int lucky;
        try {
            lucky = IntegerArgumentType.getInteger(context, "lucky");
        } catch (Exception e) {
            lucky = 20;
        }
        CommandSourceStack source = context.getSource();
        BlockPos pos;
        try {
            pos = BlockPosArgument.getBlockPos(context, "pos");
        } catch (Exception e) {
            pos = BlockPos.containing(source.getPosition());
        }
        int luckyEffectLevel = 0;
        ServerPlayer player = source.getPlayer();
        RandomSource randomSource = source.getLevel().random;
        if (player != null) {
            randomSource = player.getRandom();
            MobEffectInstance instance = player.getEffect(MobEffects.LUCK);
            if (instance != null) luckyEffectLevel = instance.getAmplifier();
        }
        LuckyBlockEffectHolder holder = manager.random(randomSource, lucky, luckyEffectLevel);
        holder.value().rewards().grant(source.getServer(), source.getLevel(), source, pos);
        source.sendSuccess(() -> Component.literal("Lucky Block Effect: %s".formatted(holder.id())), false);
        return 1;
    }
}
