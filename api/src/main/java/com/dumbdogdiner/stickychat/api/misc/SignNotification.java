package com.dumbdogdiner.stickychat.api.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a SignSpy notification.
 */
public interface SignNotification {
    /**
     * Get the player who placed the sign.
     *
     * @return {@link Player}
     */
    @NotNull
    Player getPlayer();

    /**
     * Get the location of the sign.
     *
     * @return {@link Location}
     */
    @NotNull
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
    default void destroy(@NotNull Boolean silent) {
        if (silent) {
            getLocation().getBlock().setType(Material.AIR);
        } else {
            getLocation().getBlock().breakNaturally();
        }
    }
}
