package com.xulai.criticalhit.handler;

import com.xulai.criticalhit.CritHelper;
import com.xulai.criticalhit.config.CritConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

public class CritAttackHandler {

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        ItemStack weapon = player.getMainHandItem();

        double chance = CritHelper.getTotalChance(weapon);
        boolean customCrit = chance > 0.0D && CritHelper.RANDOM.nextDouble() < chance;

        if (customCrit) {
            event.setDamageMultiplier(CritHelper.getCritDamageMultiplier(weapon));
            event.setCriticalHit(true);
            return;
        }

        if (event.isVanillaCritical() && CritConfig.isVanillaJumpCritDisabled()) {
            event.setCriticalHit(false);
        }
    }
}
