package com.dumbdogdiner.stickychat.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    Player getPlayer();

    /**
     * Get the priority of this player.
     *
     * @return {@link Priority}
     */
    @NotNull
    Priority getPriority();

    /**
     * Set the priority of this player.
     *
     * @param priority Their new priority
     */
    void setPriority(@NotNull Priority priority);

    /**
     * Checks if this player is muted. Returns true if they are.
     *
     * @return {@link Boolean}
     */
    @NotNull
    Boolean getMuted();

    /**
     * Set whether this player is muted.
     *
     * @param muted Whether this player is muted
     */
    void setMuted(@NotNull Boolean muted);

    /**
     * Gets the list of players this player has blocked.
     * @return {@link List}
     */
    @NotNull
    List<OfflinePlayer> getBlockedPlayers();

    /**
     * Get whether the target player has this player blocked.
     *
     * @param player The target player
     * @return {@link Boolean}
     */
    @NotNull
    Boolean getBlocked(@NotNull Player player);

    /**
     * Set whether the target player has this player blocked.
     *
     * @param player The target player
     * @param blocked Whether this player is blocked
     */
    void setBlocked(@NotNull Player player, @NotNull Boolean blocked);

    /**
     * Get whether this player has SignSpy enabled.
     *
     * @return {@link Boolean}
     */
    @NotNull
    Boolean getSignSpyEnabled();

    /**
     * Set whether this player has SignSpy enabled. Returns true if
     * sign spy was enabled.
     *
     * @param enabled Whether this player has sign spy enabled
     * @return {@link Boolean}
     */
    @NotNull
    Boolean setSignSpyEnabled(@NotNull Boolean enabled);

    /**
     * Save this data persistently. Returns true if the save
     * was successful.
     *
     * @return {Boolean}
     */
    @NotNull
    Boolean save();
}
