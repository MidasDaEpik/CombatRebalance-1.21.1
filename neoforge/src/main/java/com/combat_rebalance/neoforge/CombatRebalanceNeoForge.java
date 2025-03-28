package com.combat_rebalance.neoforge;

import com.combat_rebalance.CombatRebalance;
import net.neoforged.fml.common.Mod;

@Mod(CombatRebalance.MOD_ID)
public final class CombatRebalanceNeoForge {
    public CombatRebalanceNeoForge() {
        // Run our common setup.
        CombatRebalance.init();
    }
}
