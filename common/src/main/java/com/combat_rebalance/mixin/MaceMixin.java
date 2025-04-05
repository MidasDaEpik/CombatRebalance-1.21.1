package com.combat_rebalance.mixin;

import com.combat_rebalance.CombatRebalance;
import com.combat_rebalance.config.CRConfig;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

@Mixin(MaceItem.class)
public class MaceMixin {
    @Inject(method = "createAttributes", at = @At("HEAD"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<ItemAttributeModifiers> pReturn) {
        CRConfig.HANDLER.load();
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.MaceAttributeChangesEnabled) {
            ItemAttributeModifiers pModifiers = ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                                    pConfig.MaceAttackDamageAttribute - 1, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED,
                            new AttributeModifier(BASE_ATTACK_SPEED_ID,
                                    pConfig.MaceAttackSpeedAttribute - 4, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.FALL_DAMAGE_MULTIPLIER,
                            new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CombatRebalance.MOD_ID, "fall_damage_multiplier"),
                                    pConfig.MaceFallDamageMultiplierAttribute, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                            EquipmentSlotGroup.MAINHAND)
                    .build();
            pReturn.setReturnValue(pModifiers);
        }
    }

    @Inject(method = "getAttackDamageBonus", at = @At("HEAD"), cancellable = true)
    private void getAttackDamageBonus(Entity pTarget, float pDamage, DamageSource pDamageSource, CallbackInfoReturnable<Float> pReturn) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.MaceSmashCalculationChangesEnabled) {
            if (pDamageSource.getDirectEntity() instanceof LivingEntity pAttacker && canSmashAttack(pAttacker)) {
                float pFallDistance = pAttacker.fallDistance;
                float pSmashDamage = 0;
                float pCutoff1 = pConfig.MaceSmashParameter1;
                float pCutoff2 = pConfig.MaceSmashParameter2 - pCutoff1;
                float pMultiplier1 = pConfig.MaceSmashMultiplier1;
                float pMultiplier2 = pConfig.MaceSmashMultiplier2;
                float pMultiplier3 = pConfig.MaceSmashMultiplier3;

                float pSubtract = Math.min(pFallDistance, pCutoff1);
                pFallDistance -= pSubtract;
                pSmashDamage += pSubtract * pMultiplier1;

                if (pFallDistance > 0) {
                    pSubtract = Math.min(pFallDistance, pCutoff2);
                    pFallDistance -= pSubtract;
                    pSmashDamage += pSubtract * pMultiplier2 + pFallDistance * pMultiplier3;
                }

                if (pAttacker.level() instanceof ServerLevel pServerLevel) {
                    pSmashDamage += EnchantmentHelper.modifyFallBasedDamage(pServerLevel, pAttacker.getWeaponItem(), pTarget, pDamageSource, 0.0F) * pFallDistance;
                }
                pReturn.setReturnValue(pSmashDamage);

            } else {
                pReturn.setReturnValue(0.0F);
            }
        }
    }

    @WrapMethod(method = "getAttackDamageBonus")
    private float getAttackDamageBonus(Entity pTarget, float pDamage, DamageSource pDamageSource, Operation<Float> pOriginal) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.MaceSmashDamageCapEnabled) {
            if (pTarget instanceof LivingEntity pLivingEntityTarget) {
                return Math.min(pOriginal.call(pTarget, pDamage, pDamageSource), pLivingEntityTarget.getMaxHealth() * pConfig.MaceSmashDamageCap);
            } else {
                return pOriginal.call(pTarget, pDamage, pDamageSource);
            }
        } else {
            return pOriginal.call(pTarget, pDamage, pDamageSource);
        }
    }

    @Shadow
    public static boolean canSmashAttack(LivingEntity entity) {
        return true;
    }

    @WrapOperation(method = "method_58409", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;push(DDD)V"))
    private static void createAttributes(LivingEntity pLivingEntity, double pX, double pY, double pZ, Operation<Void> pOriginal) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        pOriginal.call(pLivingEntity, pX * pConfig.MaceSmashXZKnockbackMultiplier, pY * pConfig.MaceSmashYKnockbackMultiplier, pZ * pConfig.MaceSmashXZKnockbackMultiplier);
    }
}