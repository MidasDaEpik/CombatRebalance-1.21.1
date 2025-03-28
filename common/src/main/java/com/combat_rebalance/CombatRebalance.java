package com.combat_rebalance;

import com.combat_rebalance.config.CRConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CombatRebalance {
    public static final String MOD_ID = "combat_rebalance";
    public static final Logger LOGGER = LoggerFactory.getLogger(CombatRebalance.class);

    public static void init() {
        CRConfig.HANDLER.load();
    }
}
