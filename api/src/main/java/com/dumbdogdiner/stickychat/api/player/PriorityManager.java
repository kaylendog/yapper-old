package com.dumbdogdiner.stickychat.api.player;

import com.dumbdogdiner.stickychat.api.Priority;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * Manages player priority levels.
 */
public interface PriorityManager {
    /**
     * Load the priority for the target player from the database.
     * @param player The target player
     * @return True if the action was successful.
     */
    @Nullable Boolean loadPriority(Player player);

    /**
     * Return the priority for the target player.
     * @param player The target player
     * @return Their current {@link Priority} level
     */
    @Nullable Priority getPriority(Player player);

    /**
     * Set the priority of the target player.
     * @param player The target player
     * @param priority Their new {@link Priority} level
     * @return True if the action was successful.
     */
    @Nullable Boolean setPriority(Player player, Priority priority);
}
