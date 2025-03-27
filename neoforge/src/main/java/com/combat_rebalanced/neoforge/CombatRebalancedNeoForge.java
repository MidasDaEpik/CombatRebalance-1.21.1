package com.combat_rebalanced.neoforge;

import net.neoforged.fml.common.Mod;

import com.combat_rebalanced.CombatRebalanced;

@Mod(CombatRebalanced.MOD_ID)
public final class CombatRebalancedNeoForge {
    public CombatRebalancedNeoForge() {
        // Run our common setup.
        CombatRebalanced.init();
    }
}
