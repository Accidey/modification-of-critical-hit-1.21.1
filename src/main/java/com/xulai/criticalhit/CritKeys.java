package com.xulai.criticalhit;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class CritKeys {
    public static final ResourceKey<Enchantment> CRIT_CHANCE = ResourceKey.create(
            Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CriticalHitMod.MODID, "crit_chance"));
    public static final ResourceKey<Enchantment> CRIT_EFFECT = ResourceKey.create(
            Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CriticalHitMod.MODID, "crit_effect"));
}
