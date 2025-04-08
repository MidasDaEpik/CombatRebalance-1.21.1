package com.combat_rebalance.mixin;

import com.combat_rebalance.config.CRConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {
    @Unique
    private int combatRebalance_1_21_1$damageDespawnTime = -1;

    @Unique
    private int combatRebalance_1_21_1$getDamageDespawnTime() {
        return combatRebalance_1_21_1$damageDespawnTime;
    }

    @Unique
    private void combatRebalance_1_21_1$setDamageDespawnTime(int damageDespawnTime) {
        combatRebalance_1_21_1$damageDespawnTime = damageDespawnTime;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo pReturn) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.EnderPearlEntityDespawnTimeOnUserDamage >= 0) {
            ThrownEnderpearl pThis = (ThrownEnderpearl) (Object) this;
            Entity pOwner = pThis.getOwner();

            if (pOwner instanceof LivingEntity pLivingEntity) {
                if (combatRebalance_1_21_1$damageDespawnTime > 0) {
                    combatRebalance_1_21_1$damageDespawnTime -= 1;
                } else if (combatRebalance_1_21_1$damageDespawnTime == 0) {
                    Level pLevel = pThis.level();
                    if (pLivingEntity instanceof Player) {
                        pLevel.playSound(null, pThis.getX(), pThis.getY(), pThis.getZ(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.2f, 0.8f);
                        pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.2f, 0.8f);
                    } else {
                        pLevel.playSound(null, pThis.getX(), pThis.getY(), pThis.getZ(), SoundEvents.ENDER_EYE_DEATH, SoundSource.NEUTRAL, 1.2f, 0.8f);
                        pLevel.playSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.ENDER_EYE_DEATH, SoundSource.NEUTRAL, 1.2f, 0.8f);
                    }
                    pThis.discard();
                }

                if (pLivingEntity.hurtTime > 0 && combatRebalance_1_21_1$damageDespawnTime < 0) {
                    combatRebalance_1_21_1$damageDespawnTime = pConfig.EnderPearlEntityDespawnTimeOnUserDamage;
                }
            }
        }
    }
}