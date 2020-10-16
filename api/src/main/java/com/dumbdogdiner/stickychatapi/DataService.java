package com.dumbdogdiner.stickychatapi;

import com.dumbdogdiner.stickychatapi.Priority;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Handles the loading, saving, and management of player data.
 */
public interface DataService {
    /**
     * Get the player this service refers to.
     *
     * @return {@link Player}
     */
    Player getPlayer();

    /**
     * Get the priority of this player.
     *
     * @return {@link Priority}
     */
    Priority getPriority();

    /**
     * Set the priority of this player.
     *
     * @param priority Their new priority
     */
    void setPriority(Priority priority);

    /**
     * Checks if this player is muted. Returns true if they are.
     *
     * @return {@link Boolean}
     */
    Boolean getMuted();

    /**
     * Set whether this player is muted.
     *
     * @param muted Whether this player is muted
     */
    void setMuted(Boolean muted);

    /**
     * Gets the list of players this player has blocked.
     * @return {@link List}
     */
    List<OfflinePlayer> getBlockedPlayers();

    /**
     * Get whether the target player has this player blocked.
     *
     * @param player The target player
     * @return {@link Boolean}
     */
    Boolean getBlocked(Player player);

    /**
     * Set whether the target player has this player blocked.
     *
     * @param player The target player
     * @param blocked Whether this player is blocked
     */
    void setBlocked(Player player, Boolean blocked);

    /**
     * Get whether this player has SignSpy enabled.
     *
     * @return {@link Boolean}
     */
    Boolean getSignSpyEnabled();

    /**
     * Set whether this player has SignSpy enabled. Returns true if
     * sign spy was enabled.
     *
     * @param enabled Whether this player has sign spy enabled
     * @return {@link Boolean}
     */
    Boolean setSignSpyEnabled(Boolean enabled);

    /**
     * Save this data persistently. Returns true if the save
     * was successful.
     *
     * @return {Boolean}
     */
    Boolean save();
}
