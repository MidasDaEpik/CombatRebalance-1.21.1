package com.combat_rebalance.mixin;

import com.combat_rebalance.config.CRConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
    private int foodLevel;

    @Shadow
    private float saturationLevel;

    @Shadow
    private float exhaustionLevel;

    @Shadow
    private int tickTimer;

    @Shadow
    private int lastFoodLevel;

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void add(int pFoodLevel, float pSaturationLevel, CallbackInfo pCallbackInfo) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.HungerChangesEnabled && pConfig.HungerNonStackingSaturationEnabled) {

            this.foodLevel = Mth.clamp(pFoodLevel + this.foodLevel, 0, 20);
            this.saturationLevel = Mth.clamp(Math.max(pSaturationLevel, this.saturationLevel), 0.0F, (float) this.foodLevel);

            pCallbackInfo.cancel();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(Player pPlayer, CallbackInfo pCallbackInfo) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.HungerChangesEnabled) {
            FoodData pThis = (FoodData) (Object) this;
            Difficulty pDifficulty = pPlayer.level().getDifficulty();

            this.lastFoodLevel = this.foodLevel;
            if (this.exhaustionLevel > 4.0F && (pPlayer.hasEffect(MobEffects.HUNGER) || !pConfig.HungerDisableNonHealingHungerLossEnabled)) {
                this.exhaustionLevel -= 4.0F;
                if (this.saturationLevel > 0.0F) {
                    this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
                } else if (pDifficulty != Difficulty.PEACEFUL) {
                    this.foodLevel = Math.max(this.foodLevel - 1, 0);
                }
            }

            boolean pNaturalRegeneration = pPlayer.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
            if (pNaturalRegeneration && this.saturationLevel > 0.0F && this.foodLevel >= 20 && pPlayer.isHurt() && pConfig.HungerSaturationHealingEnabled) {
                ++this.tickTimer;
                if (this.tickTimer >= pConfig.HungerSaturationHealingCooldown) {
                    float f = Math.min(this.saturationLevel, 6.0F);
                    pPlayer.heal(f / 6.0F);
                    this.saturationLevel = Math.max(this.saturationLevel - f, 0.0F);
                    this.tickTimer = 0;
                }
            } else if (pNaturalRegeneration && this.foodLevel >= pConfig.HungerHealUntilHunger && pPlayer.isHurt()) {
                ++this.tickTimer;
                if (this.tickTimer > pConfig.HungerHungerHealingCooldown) {
                    pPlayer.heal(1.0F);
                    this.foodLevel -= 1;
                    if (this.saturationLevel > this.foodLevel) {
                        this.saturationLevel = this.foodLevel;
                    }
                    this.tickTimer = 0;
                }
            } else if (this.foodLevel <= 0) {
                ++this.tickTimer;
                if (this.tickTimer >= 80) {
                    if (pPlayer.getHealth() > 10.0F || pPlayer.getHealth() > 1.0F && pDifficulty == Difficulty.NORMAL || pDifficulty == Difficulty.HARD) {
                        pPlayer.hurt(pPlayer.damageSources().starve(), 1.0F);
                    }
                    this.tickTimer = 0;
                }
            } else {
                this.tickTimer = 0;
            }

            pCallbackInfo.cancel();
        }
    }
}