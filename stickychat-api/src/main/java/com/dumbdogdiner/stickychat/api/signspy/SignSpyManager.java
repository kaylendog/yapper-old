/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.signspy;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** Manages player spying on placed signs. */
public interface SignSpyManager {
    /**
     * Fetch a list of players who have SignSpy enabled.
     *
     * @return A {@link List} of {@link Player}s
     */
    default @NotNull List<Player> getSignSpyRecipients() {
        return Bukkit.getOnlinePlayers().stream().filter(this::hasSignSpyEnabled).collect(Collectors.toList());
    }

    /**
     * Test if the target player has SignSpy enabled.
     *
     * @param player The target player
     * @return True if the target player has SignSpy enabled.
     */
    @NotNull
    Boolean hasSignSpyEnabled(Player player);

    /**
     * Enable SignSpy for the target player.
     *
     * @param player The target player
     * @return True if the action was successful.
     */
    @NotNull
    Boolean enableSignSpy(Player player);

    /**
     * Disable SignSpy for the target player.
     *
     * @param player The target player
     * @return {@link Boolean}
     */
    @NotNull
    Boolean disableSignSpy(Player player);

    /**
     * Broadcast a SignSpy notification to all players on the server who have it enabled.
     *
     * @param notification The notification to send.
     */
    void broadcast(@NotNull SignNotification notification);
}
