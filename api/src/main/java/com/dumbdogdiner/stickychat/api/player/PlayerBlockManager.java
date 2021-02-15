package com.dumbdogdiner.stickychat.api.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Manages the blocking of other players.
 */
public interface PlayerBlockManager {
    /**
     * Test if the given player has blocked the target player.
     * @param player The UUID of the player
     * @param target The UUID of the player who's blocking state is being tested
     * @return True if the player has blocked the target.
     */
    @NotNull Boolean hasPlayerBlocked(UUID player, UUID target);

    /**
     * Test if the given player has blocked the target player.
     * @param player The player
     * @param target The player who's blocking state is being tested
     * @return True if the player has blocked the target.
     */
    default @NotNull Boolean hasPlayerBlocked(Player player, Player target) {
        return this.hasPlayerBlocked(player.getUniqueId(), target.getUniqueId());
    }
    /**
     * Test if the given player has blocked the target player.
     * @param player The player
     * @param target The player who's blocking state is being tested
     * @return True if the player has blocked the target.
     */
    default @NotNull Boolean hasPlayerBlocked(OfflinePlayer player, OfflinePlayer target) {
        return this.hasPlayerBlocked(player.getUniqueId(), target.getUniqueId());
    }

    /**
     * Get all outstanding blocks.
     * @return A {@link HashMap} containing all mapped blocks.
     */
    @NotNull HashMap<UUID, List<UUID>> getAllBlocks();
}
