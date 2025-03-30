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

    @SerialEntry(comment = "Rebalances the trident giving it extra damage, reach and knockback.\n" +
            "May conflict with mods that modify the trident's attributes.")
    public boolean ItemTridentAttributeChangesEnabled = true;

    @SerialEntry(comment = "Vanilla Trident Attributes: 9 Attack Damage, 1.1 Attack Speed")
    public float ItemTridentAttackDamageAttribute = 10f;

    @SerialEntry
    public float ItemTridentAttackSpeedAttribute = 1.1f;

    @SerialEntry
    public float ItemTridentEntityInteractionRangeAttribute = 1.5f;

    @SerialEntry
    public float ItemTridentAttackKnockbackAttribute = 1.0f;

    @SerialEntry(comment = "Hides items in the other hand when using Riptide.")
    public boolean ItemTridentHideAltHandWhenRiptideEnabled = true;

    @SerialEntry(comment = "\n\n\nRebalances the Mace giving it increased fall damage, giving it risk and reward.\n" +
            "May conflict with mods that modify the Mace's attributes.")
    public boolean ItemMaceAttributeChangesEnabled = true;

    @SerialEntry(comment = "Vanilla Mace Attributes: 6 Attack Damage, 0.6 Attack Speed")
    public float ItemMaceAttackDamageAttribute = 6f;

    @SerialEntry
    public float ItemMaceAttackSpeedAttribute = 0.6f;

    @SerialEntry
    public float ItemMaceFallDamageMultiplierAttribute = 1.0f;

    @SerialEntry(comment = "Changes to the Mace's Smash Attack Damage Calculation.\n" +
            "May conflict with mods that modify the Mace's damage calculation.")
    public boolean ItemMaceSmashCalculationChangesEnabled = true;

    @SerialEntry(comment = "Damage Calculation: Fall Distance Under Cutoff 1 * Multiplier 1 + Fall Distance Between Cutoff 1 and 2 * Multiplier 2 + Fall Distance Above Cutoff 2 * Multiplier 3")
    public float ItemMaceSmashCutoff1 = 8.0f;

    @SerialEntry
    public float ItemMaceSmashCutoff2 = 16.0f;

    @SerialEntry
    public float ItemMaceSmashMultiplier1 = 2.0f;

    @SerialEntry
    public float ItemMaceSmashMultiplier2 = 1.0f;

    @SerialEntry
    public float ItemMaceSmashMultiplier3 = 0.5f;

    @SerialEntry(comment = "Caps the Mace's Smash Attack based on the Target's Health (Target Health * Smash Damage Cap)")
    public boolean ItemMaceSmashDamageCapEnabled = true;

    @SerialEntry
    public float ItemMaceSmashDamageCap = 1.5f;

    @SerialEntry(comment = "Multiplier to the Knockback dealt with the Mace's Smash Attack")
    public float ItemMaceSmashXZKnockbackMultiplier = 1.0f;

    @SerialEntry
    public float ItemMaceSmashYKnockbackMultiplier = 1.0f;
}
