package com.dumbdogdiner.stickychat.api.player;

import com.dumbdogdiner.stickychat.api.Priority;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** Manages player priority levels. */
public interface PriorityManager {
    /**
     * Load the priority for the target player from the database.
     *
     * @param player The target player
     * @return True if the action was successful.
     */
    @NotNull
    Boolean loadPriority(Player player);

    /**
     * Return the priority for the target player.
     *
     * @param player The target player
     * @return Their current {@link Priority} level
     */
    @NotNull
    Priority getPriority(Player player);

    /**
     * Set the priority of the target player.
     *
     * @param player The target player
     * @param priority Their new {@link Priority} level
     * @return True if the action was successful.
     */
    @NotNull
    Boolean setPriority(Player player, Priority priority);
}
