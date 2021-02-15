package com.dumbdogdiner.stickychat.api.player;

import com.dumbdogdiner.stickychat.api.Priority;
import org.bukkit.entity.Player;

/**
 * Manages player priority levels.
 */
public interface PriorityManager {
    /**
     * Return the priority for the target player.
     * @param player The target player
     * @return Their current {@link Priority} level
     */
    Priority getPriority(Player player);

    /**
     * Set the priority of the target player.
     * @param player The target player
     * @param priority Their new {@link Priority} level
     * @return True if the action was successful.
     */
    Boolean setPriority(Player player, Priority priority);
}
