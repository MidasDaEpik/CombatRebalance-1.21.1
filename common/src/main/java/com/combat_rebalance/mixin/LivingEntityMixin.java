package com.combat_rebalance.mixin;

import com.combat_rebalance.config.CRConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void eat(Level pLevel, ItemStack pFood, FoodProperties pFoodProperties, CallbackInfoReturnable<ItemStack> pReturn) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.FoodCooldownSystemEnabled) {
            LivingEntity pThis = (LivingEntity) (Object) this;
            if (pLevel instanceof ServerLevel && pThis instanceof Player pPlayer) {
                int pCooldown = 0;
                for(FoodProperties.PossibleEffect pFoodPropertiesEffect : pFoodProperties.effects()) {
                    MobEffectCategory pCategory = pFoodPropertiesEffect.effect().getEffect().value().getCategory();
                    if (pCategory == MobEffectCategory.BENEFICIAL) {
                        pCooldown += (int) Math.ceil(((pFoodPropertiesEffect.effect().getAmplifier() + 1) * pConfig.FoodCooldownEffectBasedPositiveCooldown) * pFoodPropertiesEffect.probability());
                    } else if (pCategory == MobEffectCategory.HARMFUL) {
                        pCooldown -= (int) Math.ceil(((pFoodPropertiesEffect.effect().getAmplifier() + 1) * pConfig.FoodCooldownEffectBasedNegativeCooldown) * pFoodPropertiesEffect.probability());
                    }
                }
                pCooldown = Math.max(pCooldown, pConfig.GlobalFoodCooldownDuration);
                if (pCooldown > 0) {
                    pPlayer.getCooldowns().addCooldown(pFood.getItem(), pCooldown);
                }
            }
        }
    }
}