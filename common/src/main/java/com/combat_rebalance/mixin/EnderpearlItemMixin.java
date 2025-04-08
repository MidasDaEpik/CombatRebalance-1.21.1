package com.combat_rebalance.mixin;

import com.combat_rebalance.config.CRConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderpearlItem.class)
public class EnderpearlItemMixin {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"))
    private void use(ItemCooldowns pInstance, Item pItem, int pTicks, Operation<Void> pOriginal) {
        CRConfig pConfig = CRConfig.HANDLER.instance();
        if (pConfig.EnderPearlCooldownOnUse >= 0) {
            pOriginal.call(pInstance, pItem, pConfig.EnderPearlCooldownOnUse);
        } else {
            pOriginal.call(pInstance, pItem, pTicks);
        }
    }
}