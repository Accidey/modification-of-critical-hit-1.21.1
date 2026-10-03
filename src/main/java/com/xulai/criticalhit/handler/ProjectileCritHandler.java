package com.xulai.criticalhit.handler;

import com.xulai.criticalhit.CritHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ProjectileCritHandler {

    private static final String TAG_CRIT = "criticalhit.crit";
    private static final String TAG_MULT = "criticalhit.mult";

    @SubscribeEvent
    public static void onProjectileJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof Projectile projectile)) {
            return;
        }
        if (!(projectile.getOwner() instanceof Player player)) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        double mainChance = CritHelper.getTotalChance(mainHand);
        double offChance = CritHelper.getTotalChance(offHand);
        double chance = Math.max(mainChance, offChance);
        if (chance <= 0.0D) {
            return;
        }

        ItemStack used = mainChance >= offChance ? mainHand : offHand;
        if (CritHelper.RANDOM.nextDouble() >= chance) {
            return;
        }

        CompoundTag data = projectile.getPersistentData();
        data.putBoolean(TAG_CRIT, true);
        data.putDouble(TAG_MULT, CritHelper.getCritDamageMultiplier(used));
    }

    @SubscribeEvent
    public static void onProjectileHit(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof Projectile projectile)) {
            return;
        }
        CompoundTag data = projectile.getPersistentData();
        if (!data.getBoolean(TAG_CRIT)) {
            return;
        }

        double multiplier = data.getDouble(TAG_MULT);
        data.remove(TAG_CRIT);
        data.remove(TAG_MULT);
        event.setAmount((float) (event.getAmount() * multiplier));

        LivingEntity target = event.getEntity();
        target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
