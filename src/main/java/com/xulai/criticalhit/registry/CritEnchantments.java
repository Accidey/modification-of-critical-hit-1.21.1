package com.xulai.criticalhit.registry;

import com.xulai.criticalhit.CritKeys;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public final class CritEnchantments {

    private CritEnchantments() {
    }

    public static boolean isCritChance(Enchantment enchantment) {
        return CritKeys.CRIT_CHANCE.equals(keyOf(enchantment));
    }

    public static boolean isCritEffect(Enchantment enchantment) {
        return CritKeys.CRIT_EFFECT.equals(keyOf(enchantment));
    }

    private static ResourceKey<Enchantment> keyOf(Enchantment enchantment) {
        try {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server == null) {
                return null;
            }
            Registry<Enchantment> registry = server.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            return registry.getResourceKey(enchantment).orElse(null);
        } catch (RuntimeException exception) {
            return null;
        }
    }
}
