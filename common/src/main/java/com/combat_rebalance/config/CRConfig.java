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
            "May conflict with other mods that modify the trident's attributes, disable if so.")
    public boolean ItemTridentChangesEnabled = true;

    @SerialEntry(comment = "Vanilla Trident Attributes: 9 Attack Damage, 1.1 Attack Speed")
    public float ItemTridentAttackDamage = 10f;

    @SerialEntry
    public float ItemTridentAttackSpeed = 1.1f;

    @SerialEntry
    public float ItemTridentEntityInteractionRange = 1.5f;

    @SerialEntry
    public float ItemTridentAttackKnockback = 1.0f;

    @SerialEntry(comment = "\n\n\nRebalances the mace giving it increased fall damage, giving it risk and reward\n" +
            "May conflict with other mods that modify the mace's attributes, disable if so.")
    public boolean ItemMaceChangesEnabled = true;

    @SerialEntry(comment = "Vanilla Mace Attributes: 6 Attack Damage, 0.6 Attack Speed")
    public float ItemMaceAttackDamage = 6f;

    @SerialEntry
    public float ItemMaceAttackSpeed = 0.6f;

    @SerialEntry
    public float ItemMaceFallDamageMultiplier = 0.75f;
}
