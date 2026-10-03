package com.xulai.criticalhit.client;

import com.xulai.criticalhit.CriticalHitMod;
import com.xulai.criticalhit.CritHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Locale;

@EventBusSubscriber(modid = CriticalHitMod.MODID, value = Dist.CLIENT)
public class CritTooltipHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!CritHelper.isTooltipRelevant(stack)) {
            return;
        }

        double chance = CritHelper.getTotalChance(stack);
        if (chance <= 0.0D && !CritHelper.hasDamageSource(stack)) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        int speedIndex = findAttackSpeedIndex(tooltip, stack);
        int index = speedIndex >= 0 ? speedIndex + 1 : tooltip.size();

        if (chance > 0.0D) {
            tooltip.add(index++, Component.translatable("tooltip.criticalhit.crit_chance",
                            String.format(Locale.ROOT, "%.0f%%", chance * 100.0D))
                    .withStyle(ChatFormatting.BLUE));
        }
        if (CritHelper.hasDamageSource(stack)) {
            float damage = CritHelper.getCritDamageMultiplier(stack);
            if (damage > 0.0F) {
                tooltip.add(index, Component.translatable("tooltip.criticalhit.crit_damage",
                                String.format(Locale.ROOT, "%.0f%%", damage * 100.0F))
                        .withStyle(ChatFormatting.BLUE));
            }
        }
    }

    private static int findAttackSpeedIndex(List<Component> tooltip, ItemStack stack) {
        double speed = getAttackSpeed(stack);
        String speedValue = " " + java.text.MessageFormat.format("{0,number,#.##}", speed);
        String attrName = Component.translatable(Attributes.ATTACK_SPEED.value().getDescriptionId()).getString();
        for (int i = 0; i < tooltip.size(); i++) {
            String text = tooltip.get(i).getString();
            if (text.endsWith(attrName) && text.contains(speedValue)) {
                return i;
            }
        }
        return -1;
    }

    private static double getAttackSpeed(ItemStack stack) {
        return stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS,
                        net.minecraft.world.item.component.ItemAttributeModifiers.EMPTY)
                .compute(4.0, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
    }
}
