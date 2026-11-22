package com.xulai.criticalhit.mixin;

import com.xulai.criticalhit.config.CritConfig;
import com.xulai.criticalhit.registry.CritEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMaxLevelMixin {

    @Inject(method = "getMaxLevel", at = @At("RETURN"), cancellable = true)
    private void criticalhit$capMaxLevel(CallbackInfoReturnable<Integer> cir) {
        Enchantment self = (Enchantment) (Object) this;
        int limit;
        if (CritEnchantments.isCritChance(self)) {
            limit = CritConfig.getChanceMaxLevel();
        } else if (CritEnchantments.isCritEffect(self)) {
            limit = CritConfig.getEffectMaxLevel();
        } else {
            return;
        }
        cir.setReturnValue(Math.min(cir.getReturnValue(), limit));
    }
}
