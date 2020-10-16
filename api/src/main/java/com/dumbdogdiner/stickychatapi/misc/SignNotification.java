package com.dumbdogdiner.stickychatapi.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Represents a SignSpy notification.
 */
public interface SignNotification {
    /**
     * Get the player who placed the sign.
     *
     * @return {@link Player}
     */
    Player getPlayer();

    /**
     * Get the location of the sign.
     *
     * @return {@link Location}
     */
    Location getLocation();

    /**
     * Destroy the placed sign.
     */
    default void destroy() {
        destroy(false);
    }

    /**
     * Destroy the placed sign, specifying if the block should be destroyed
     * silently.
     *
     * @param silent Whether the block should be destroyed silently.
     */
    default void destroy(Boolean silent) {
        if (silent) {
            getLocation().getBlock().setType(Material.AIR);
        } else {
            getLocation().getBlock().breakNaturally();
        }
    }
}
