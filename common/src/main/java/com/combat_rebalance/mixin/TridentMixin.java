package com.combat_rebalance.mixin;

import com.combat_rebalance.CombatRebalance;
import com.combat_rebalance.config.CRConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

@Mixin(TridentItem.class)
public class TridentMixin {
    @Inject(method = "createAttributes", at = @At("HEAD"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<ItemAttributeModifiers> pReturn) {
        CRConfig.HANDLER.load();
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.TridentAttributeChangesEnabled) {
            ItemAttributeModifiers pModifiers = ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                                    pConfig.TridentAttackDamageAttribute - 1, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED,
                            new AttributeModifier(BASE_ATTACK_SPEED_ID,
                                    pConfig.TridentAttackSpeedAttribute - 4, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ENTITY_INTERACTION_RANGE,
                            new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CombatRebalance.MOD_ID, "entity_interaction_range"),
                                    pConfig.TridentEntityInteractionRangeAttribute, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_KNOCKBACK,
                            new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CombatRebalance.MOD_ID, "attack_knockback"),
                                    pConfig.TridentAttackKnockbackAttribute, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.MAINHAND)
                    .build();
            pReturn.setReturnValue(pModifiers);
        }
    }
}