package com.combat_rebalanced.mixin;

import com.combat_rebalanced.CombatRebalanced;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;
import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

@Mixin(MaceItem.class)
public class MaceMixin {
    @Inject(method = "createAttributes", at = @At("HEAD"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<ItemAttributeModifiers> pReturn) {
        ItemAttributeModifiers pModifiers = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 5.0f, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.4f, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.FALL_DAMAGE_MULTIPLIER,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CombatRebalanced.MOD_ID, "fall_damage_multiplier"),
                                0.75f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.MAINHAND)
                .build();
        pReturn.setReturnValue(pModifiers);
    }
}