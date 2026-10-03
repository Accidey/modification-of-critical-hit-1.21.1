package com.xulai.criticalhit.command;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.xulai.criticalhit.config.CritConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;

public class CritCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("criticalhit")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("chance")
                        .then(Commands.argument("value", DoubleArgumentType.doubleArg(0.0, 1.0))
                                .executes(context -> setHeldWeapon(context, true))))
                .then(Commands.literal("damage")
                        .then(Commands.argument("value", DoubleArgumentType.doubleArg(1.0, 100.0))
                                .executes(context -> setHeldWeapon(context, false)))));
    }

    private static int setHeldWeapon(CommandContext<CommandSourceStack> context, boolean isChance) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) {
            context.getSource().sendFailure(Component.translatable("command.criticalhit.no_item"));
            return 0;
        }

        double value = DoubleArgumentType.getDouble(context, "value");
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();

        List<String> list = new ArrayList<>(CritConfig.CUSTOM_WEAPONS.get());
        int found = -1;
        for (int i = 0; i < list.size(); i++) {
            String[] parts = list.get(i).split(",");
            if (parts.length == 3 && parts[0].trim().equals(id)) {
                found = i;
                break;
            }
        }

        double chance = isChance ? value : 0.2;
        double damage = isChance ? 1.5 : value;
        if (found >= 0) {
            String[] parts = list.get(found).split(",");
            if (isChance) {
                damage = Double.parseDouble(parts[2].trim());
            } else {
                chance = Double.parseDouble(parts[1].trim());
            }
            list.set(found, id + "," + chance + "," + damage);
        } else {
            list.add(id + "," + chance + "," + damage);
        }

        CritConfig.CUSTOM_WEAPONS.set(List.copyOf(list));
        CritConfig.saveAndReload();

        double finalChance = chance;
        double finalDamage = damage;
        context.getSource().sendSuccess(() -> Component.translatable("command.criticalhit.set", id,
                Math.round(finalChance * 100), Math.round((finalDamage - 1) * 100)), true);
        return 1;
    }
}
