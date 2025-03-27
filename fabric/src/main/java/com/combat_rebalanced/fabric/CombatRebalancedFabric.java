package com.combat_rebalanced.fabric;

import net.fabricmc.api.ModInitializer;

import com.combat_rebalanced.CombatRebalanced;

public final class CombatRebalancedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        CombatRebalanced.init();
    }
}
