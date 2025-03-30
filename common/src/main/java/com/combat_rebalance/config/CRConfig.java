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

    @SerialEntry(value = "Trident Attribute Changes Enabled?",
            comment = "Rebalances the trident giving it extra damage, reach and knockback.\n" +
            "May conflict with mods that modify the trident's attributes.")
    public boolean ItemTridentAttributeChangesEnabled = true;

    @SerialEntry(value = "Trident Attack Damage Attribute",
            comment = "Vanilla Trident Attributes: 9 Attack Damage, 1.1 Attack Speed")
    public float ItemTridentAttackDamageAttribute = 10f;

    @SerialEntry(value = "Trident Attack Speed Attribute")
    public float ItemTridentAttackSpeedAttribute = 1.1f;

    @SerialEntry(value = "Trident Reach Attribute")
    public float ItemTridentEntityInteractionRangeAttribute = 1.5f;

    @SerialEntry(value = "Trident Knockback Attribute")
    public float ItemTridentAttackKnockbackAttribute = 1.0f;

    @SerialEntry(value = "Trident Hide Alt Hand When Riptide Enabled?",
            comment = "\nHides items in the other hand when using Riptide.")
    public boolean ItemTridentHideAltHandWhenRiptideEnabled = true;

    @SerialEntry(value = "Mace Attribute Changes Enabled?",
            comment = "\n\n\nRebalances the Mace giving it increased fall damage, giving it risk and reward.\n" +
            "May conflict with mods that modify the Mace's attributes.")
    public boolean ItemMaceAttributeChangesEnabled = true;

    @SerialEntry(value = "Mace Attack Damage Attribute",
            comment = "Vanilla Mace Attributes: 6 Attack Damage, 0.6 Attack Speed")
    public float ItemMaceAttackDamageAttribute = 6f;

    @SerialEntry(value = "Mace Attack Speed Attribute")
    public float ItemMaceAttackSpeedAttribute = 0.6f;

    @SerialEntry(value = "Mace Fall Damage Multiplier Attribute")
    public float ItemMaceFallDamageMultiplierAttribute = 1.0f;

    @SerialEntry(value = "Mace Smash Calculation Changes Enabled?",
            comment = "\nChanges to the Mace's Smash Attack Damage Calculation.\n" +
            "May conflict with mods that modify the Mace's damage calculation.")
    public boolean ItemMaceSmashCalculationChangesEnabled = true;

    @SerialEntry(value = "Mace Smash Parameter 1",
            comment = "Damage Calculation: Fall Distance Under Parameter 1 * Multiplier 1 + Fall Distance Between Parameter 1 and 2 * Multiplier 2 + Fall Distance Above Parameter 2 * Multiplier 3")
    public float ItemMaceSmashParameter1 = 8.0f;

    @SerialEntry(value = "Mace Smash Parameter 2")
    public float ItemMaceSmashParameter2 = 16.0f;

    @SerialEntry(value = "Mace Smash Multiplier 1")
    public float ItemMaceSmashMultiplier1 = 2.0f;

    @SerialEntry(value = "Mace Smash Multiplier 2")
    public float ItemMaceSmashMultiplier2 = 1.0f;

    @SerialEntry(value = "Mace Smash Multiplier 3")
    public float ItemMaceSmashMultiplier3 = 0.5f;

    @SerialEntry(value = "Mace Smash Damage Cap Enabled?",
            comment = "\nCaps the Mace's Smash Attack based on the Target's Health (Target Health * Smash Damage Cap)")
    public boolean ItemMaceSmashDamageCapEnabled = true;

    @SerialEntry(value = "Mace Smash Damage Cap Multiplier")
    public float ItemMaceSmashDamageCap = 1.5f;

    @SerialEntry(value = "Mace Smash Knockback XZ Multiplier",
            comment = "\nMultiplier to the Knockback dealt with the Mace's Smash Attack")
    public float ItemMaceSmashXZKnockbackMultiplier = 1.0f;

    @SerialEntry(value = "Mace Smash Knockback Y Multiplier")
    public float ItemMaceSmashYKnockbackMultiplier = 1.0f;
}
