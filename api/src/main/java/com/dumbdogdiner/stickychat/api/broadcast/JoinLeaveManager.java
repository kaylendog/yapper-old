package com.dumbdogdiner.stickychat.api.broadcast;

import org.bukkit.entity.Player;

/**
 * Manages joins and leaves.
 */
public interface JoinLeaveManager {
    /**
     * Handle a player joining this server.
     * @param player
     */
    void handlePlayerJoin(Player player);
}
