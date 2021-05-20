package com.dumbdogdiner.stickychat.api.player;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** Manages the blocking of other players. */
public interface PlayerBlockManager {
    /**
     * Load block information for the target player.
     *
     * @param player The target player
     * @return True if the action was successful
     */
    @NotNull
    Boolean loadBlocks(@NotNull UUID player);

    /**
     * Block the target player. Returns false if the player is already blocked.
     *
     * @param player The uuid of the player who is blocking the target
     * @param target The uuid of the target to block
     * @return True if the player was blocked, false if they were already blocked.
     */
    @NotNull
    Boolean block(@NotNull UUID player, @NotNull UUID target);

    /**
     * Block the target player. Returns false if the player is already blocked.
     *
     * @param player The player who is blocking the target
     * @param target The target to block
     * @return True if the player was blocked, false if they were already blocked.
     */
    default @NotNull Boolean block(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target) {
        return this.block(player.getUniqueId(), target.getUniqueId());
    }

    /**
     * Block the target player. Returns false if the player is already blocked.
     *
     * @param player The uuid of the player who is blocking the target
     * @param target The uuid of the target to block
     * @return True if the player was blocked, false if they were already blocked.
     */
    default @NotNull Boolean block(@NotNull Player player, @NotNull Player target) {
        return this.block(player.getUniqueId(), target.getUniqueId());
    }

    /**
     * Unblock the target player.
     *
     * @param player The uuid of the player who is unblocking the target
     * @param target The uuid of the target to unblock
     * @return True if the player was unblocked, false if they were not blocked to begin with.
     */
    @NotNull
    Boolean unblock(@NotNull UUID player, @NotNull UUID target);

    /**
     * Unblock the target player.
     *
     * @param player The uuid of the player who is unblocking the target
     * @param target The uuid of the target to unblock
     * @return True if the player was unblocked, false if they were not blocked to begin with.
     */
    default @NotNull Boolean unblock(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target) {
        return this.unblock(player.getUniqueId(), target.getUniqueId());
    }

    /**
     * Unblock the target player.
     *
     * @param player The uuid of the player who is unblocking the target
     * @param target The uuid of the target to unblock
     * @return True if the player was unblocked, false if they were not blocked to begin with.
     */
    default @NotNull Boolean unblock(@NotNull Player player, @NotNull Player target) {
        return this.unblock(player.getUniqueId(), target.getUniqueId());
    }

    /**
     * Test if the given player has blocked the target player.
     *
     * @param player The UUID of the player
     * @param target The UUID of the player who's blocking state is being tested
     * @return True if the player has blocked the target.
     */
    @NotNull
    Boolean hasBlocked(@NotNull UUID player, @NotNull UUID target);

    /**
     * Test if the given player has blocked the target player.
     *
     * @param player The player
     * @param target The player who's blocking state is being tested
     * @return True if the player has blocked the target.
     */
    default @NotNull Boolean hasBlocked(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target) {
        return this.hasBlocked(player.getUniqueId(), target.getUniqueId());
    }

    /**
     * Test if the given player has blocked the target player.
     *
     * @param player The player
     * @param target The player who's blocking state is being tested
     * @return True if the player has blocked the target.
     */
    default @NotNull Boolean hasBlocked(@NotNull Player player, @NotNull Player target) {
        return this.hasBlocked(player.getUniqueId(), target.getUniqueId());
    }

    /**
     * Get all outstanding blocks.
     *
     * @return A {@link HashMap} containing all mapped blocks.
     */
    @NotNull
    HashMap<UUID, Set<UUID>> getAllBlocks();
}
