package com.combat_rebalance.config;

import com.combat_rebalance.CombatRebalance;
import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;

public class CRConfig {
    public static ConfigClassHandler<CRConfig> HANDLER = ConfigClassHandler.createBuilder(CRConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath(CombatRebalance.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(Platform.getConfigFolder().resolve("combat_rebalance_config.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(value = "Hunger Changes Enabled?",
            comment = "\n\n\nMake healing more steady in combat rather than in bursts with food, \n" +
                    "making high saturation foods not as overpowered as they are. \n" +
                    "May conflict with mods that also rebalance the hunger system")
    public boolean HungerChangesEnabled = true;

    @SerialEntry(value = "[Hunger] Non Stacking Saturation Enabled?",
            comment = "\nEating multiple foods in succession does not stack saturation.")
    public boolean HungerNonStackingSaturationEnabled = true;

    @SerialEntry(value = "[Hunger] Saturation Healing Enabled?",
            comment = "\nSetting to True enables saturation being consumed to heal the player.")
    public boolean HungerSaturationHealingEnabled = false;

    @SerialEntry(value = "[Hunger] Saturation Healing Cooldown",
            comment = "\nCooldown per tick of Healing from Saturation, useless unless Saturation Healing is enabled. (In ticks, 20 ticks = 1 second)" +
                    "\nVanilla Value: 10 ticks / 0.5 seconds")
    public int HungerSaturationHealingCooldown = 10;

    @SerialEntry(value = "[Hunger] Heal Until Hunger",
            comment = "\nUntil what amount of Hunger will be consumed to heal the player. (Full Bar is 20 Hunger, Sprinting is disabled at 6 Hunger)" +
                    "\nVanilla Value: 17 hunger / 8.5 points on hunger bar")
    public int HungerHealUntilHunger = 8;

    @SerialEntry(value = "[Hunger] Hunger Healing Cooldown",
            comment = "\nCooldown per tick of Healing from Hunger. (In ticks, 20 ticks = 1 second)" +
                    "\nVanilla Value: 80 ticks / 4 seconds")
    public int HungerHungerHealingCooldown = 40;

    //Note: Add Hunger Effect to This
    @SerialEntry(value = "[Hunger] Disable Non-Healing Hunger Loss?",
            comment = "\nSetting to True disables all hunger loss except for healing.")
    public boolean HungerDisableNonHealingHungerLossEnabled = false;

    @SerialEntry(value = "Trident Attribute Changes Enabled?",
            comment = "Rebalances the trident giving it extra damage, reach and knockback.\n" +
            "May conflict with mods that modify the trident's attributes.")
    public boolean TridentAttributeChangesEnabled = true;

    @SerialEntry(value = "[Trident Attribute] Attack Damage",
            comment = "Vanilla Trident Attributes: 9 Attack Damage, 1.1 Attack Speed")
    public float TridentAttackDamageAttribute = 10f;

    @SerialEntry(value = "[Trident Attribute] Attack Speed")
    public float TridentAttackSpeedAttribute = 1.1f;

    @SerialEntry(value = "[Trident Attribute] Reach")
    public float TridentEntityInteractionRangeAttribute = 1.5f;

    @SerialEntry(value = "[Trident Attribute] Knockback")
    public float TridentAttackKnockbackAttribute = 1.0f;

    @SerialEntry(value = "Trident Hide Alt Hand When Riptide Enabled?",
            comment = "\nHides items in the other hand when using Riptide.")
    public boolean TridentHideAltHandWhenRiptideEnabled = true;

    @SerialEntry(value = "Mace Attribute Changes Enabled?",
            comment = "\n\n\nRebalances the Mace giving it increased fall damage, giving it risk and reward.\n" +
            "May conflict with mods that modify the Mace's attributes.")
    public boolean MaceAttributeChangesEnabled = true;

    @SerialEntry(value = "[Mace Attribute] Attack",
            comment = "Vanilla Mace Attributes: 6 Attack Damage, 0.6 Attack Speed")
    public float MaceAttackDamageAttribute = 6f;

    @SerialEntry(value = "[Mace Attribute] Attack Speed")
    public float MaceAttackSpeedAttribute = 0.6f;

    @SerialEntry(value = "[Mace Attribute] Fall Damage Multiplier")
    public float MaceFallDamageMultiplierAttribute = 1.0f;

    @SerialEntry(value = "Mace Smash Calculation Changes Enabled?",
            comment = "\nChanges to the Mace's Smash Attack Damage Calculation.\n" +
            "May conflict with mods that modify the Mace's damage calculation.")
    public boolean MaceSmashCalculationChangesEnabled = true;

    @SerialEntry(value = "[Mace Smash] Parameter 1",
            comment = "Damage Calculation: Fall Distance Under Parameter 1 * Multiplier 1 + Fall Distance Between Parameter 1 and 2 * Multiplier 2 + Fall Distance Above Parameter 2 * Multiplier 3")
    public float MaceSmashParameter1 = 8.0f;

    @SerialEntry(value = "[Mace Smash] Parameter 2")
    public float MaceSmashParameter2 = 16.0f;

    @SerialEntry(value = "[Mace Smash] Multiplier 1")
    public float MaceSmashMultiplier1 = 2.0f;

    @SerialEntry(value = "[Mace Smash] Multiplier 2")
    public float MaceSmashMultiplier2 = 1.0f;

    @SerialEntry(value = "[Mace Smash] Multiplier 3")
    public float MaceSmashMultiplier3 = 0.5f;

    @SerialEntry(value = "Mace Smash Damage Cap Enabled?",
            comment = "\nCaps the Mace's Smash Attack based on the Target's Health (Target Health * Smash Damage Cap)")
    public boolean MaceSmashDamageCapEnabled = true;

    @SerialEntry(value = "[Mace Smash Damage Cap] Multiplier")
    public float MaceSmashDamageCap = 1.5f;

    @SerialEntry(value = "Mace Smash Knockback XZ Multiplier",
            comment = "\nMultiplier to the Knockback dealt with the Mace's Smash Attack")
    public float MaceSmashXZKnockbackMultiplier = 1.0f;

    @SerialEntry(value = "Mace Smash Knockback Y Multiplier")
    public float MaceSmashYKnockbackMultiplier = 1.0f;
}
