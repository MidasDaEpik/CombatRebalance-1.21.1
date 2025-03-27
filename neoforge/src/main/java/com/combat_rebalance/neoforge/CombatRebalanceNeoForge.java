package com.combat_rebalance.neoforge;

import net.neoforged.fml.common.Mod;

import com.combat_rebalance.CombatRebalance;

@Mod(CombatRebalance.MOD_ID)
public final class CombatRebalanceNeoForge {
    public CombatRebalanceNeoForge() {
        // Run our common setup.
        CombatRebalance.init();
    }
}
