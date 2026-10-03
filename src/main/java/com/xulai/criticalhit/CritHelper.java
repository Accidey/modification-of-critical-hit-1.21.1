package com.xulai.criticalhit;

import com.xulai.criticalhit.config.CritConfig;
import com.xulai.criticalhit.config.WeaponCrit;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Random;

public class CritHelper {

    public static final Random RANDOM = new Random();

    public static final TagKey<Item> CRIT_ENCHANTABLE = TagKey.create(
            Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CriticalHitMod.MODID, "crit_enchantable"));

    public static int getChanceLevel(ItemStack stack) {
        if (!CritConfig.isChanceEnabled()) {
            return 0;
        }
        return Math.min(getEnchantLevel(stack, CritKeys.CRIT_CHANCE), CritConfig.getChanceMaxLevel());
    }

    public static int getEffectLevel(ItemStack stack) {
        if (!CritConfig.isEffectEnabled()) {
            return 0;
        }
        return Math.min(getEnchantLevel(stack, CritKeys.CRIT_EFFECT), CritConfig.getEffectMaxLevel());
    }

    private static int getEnchantLevel(ItemStack stack, ResourceKey<Enchantment> key) {
        if (stack.isEmpty()) {
            return 0;
        }
        for (var entry : stack.getTagEnchantments().entrySet()) {
            if (entry.getKey().is(key)) {
                return entry.getIntValue();
            }
        }
        return 0;
    }

    public static double getWeaponChance(ItemStack stack) {
        if (stack.isEmpty()) {
            return -1.0D;
        }
        WeaponCrit crit = CritConfig.getCustomWeapons().get(stack.getItem());
        return crit != null ? crit.critChance() : -1.0D;
    }

    public static double getWeaponDamage(ItemStack stack) {
        if (stack.isEmpty()) {
            return -1.0D;
        }
        WeaponCrit crit = CritConfig.getCustomWeapons().get(stack.getItem());
        return crit != null ? crit.critDamage() : -1.0D;
    }

    public static boolean isConfigured(ItemStack stack) {
        return !stack.isEmpty() && CritConfig.getCustomWeapons().containsKey(stack.getItem());
    }

    public static boolean isCritWeapon(ItemStack stack) {
        return !stack.isEmpty() && stack.is(CRIT_ENCHANTABLE);
    }

    public static boolean hasChanceSource(ItemStack stack) {
        return getChanceLevel(stack) > 0 || getWeaponChance(stack) > 0.0D;
    }

    public static boolean hasDamageSource(ItemStack stack) {
        return getEffectLevel(stack) > 0 || getWeaponDamage(stack) > 0.0D;
    }

    public static boolean isTooltipRelevant(ItemStack stack) {
        return isConfigured(stack) || isCritWeapon(stack) || hasChanceSource(stack);
    }

    public static double getTotalChance(ItemStack stack) {
        double chance = CritConfig.getDefaultChance();
        double weaponChance = getWeaponChance(stack);
        if (weaponChance > 0.0D) {
            chance += weaponChance;
        }
        chance += getChanceLevel(stack) * CritConfig.getChancePerLevel();
        return Math.min(1.0D, chance);
    }

    public static float getCritDamageMultiplier(ItemStack stack) {
        double weaponDamage = getWeaponDamage(stack);
        double base = weaponDamage >= 0.0D ? weaponDamage : CritConfig.getEffectInitial();
        return (float) (base + CritConfig.getEffectPerLevel() * getEffectLevel(stack));
    }
}
