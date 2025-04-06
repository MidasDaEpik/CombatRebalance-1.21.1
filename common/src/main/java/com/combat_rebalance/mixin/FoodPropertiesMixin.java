package com.combat_rebalance.mixin;

import com.combat_rebalance.config.CRConfig;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodProperties.class)
public class FoodPropertiesMixin {
    @Shadow
    @Final
    private float saturation;

    @Shadow
    @Final
    private int nutrition;

    @Inject(method = "eatDurationTicks", at = @At("HEAD"), cancellable = true)
    private void eat(CallbackInfoReturnable<Integer> pReturn) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.FoodEatTimeRebalanceEnabled) {
            int pTime = (int) Math.ceil(Math.clamp(this.nutrition * pConfig.FoodEatTimeDurationFromNutritionValue + this.saturation * pConfig.FoodEatTimeDurationFromSaturationValue, pConfig.FoodEatTimeMinimumValueCap, pConfig.FoodEatTimeMaximumValueCap));
            pReturn.setReturnValue(pTime);
        }
    }
}