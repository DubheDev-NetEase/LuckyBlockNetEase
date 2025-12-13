package dev.dubhe.lucky.command;

import com.google.common.base.Supplier;
import com.mojang.brigadier.context.CommandContext;
import dev.dubhe.lucky.LuckyBlockMod;
import dev.dubhe.lucky.effect.LuckyBlockEffectHolder;
import dev.dubhe.lucky.server.ServerLuckyBlockEffectManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
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
            Commands.literal("lb").requires(source -> source.hasPermission(Commands.LEVEL_ADMINS)).then(
                Commands.literal("effects").executes(ModCommands::effects)
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
}
