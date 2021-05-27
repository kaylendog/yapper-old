/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.event;

import org.bukkit.entity.Player;

/**
 * An event that fires when a player joins. leaves, or arrives from another server on the network.
 */
public class JoinLeaveBroadcastEvent extends ChatEvent {
    public enum Type {
        JOIN_NETWORK, LEAVE_NETWORK, JOIN, LEAVE
    }

    private final Player player;
    private final Type type;
    private final String content;

    /**
     * Construct a new event.
     *
     * @param player The target player
     * @param type The type of this event
     * @param content The raw unformatted content
     */
    public JoinLeaveBroadcastEvent(Player player, Type type, String content) {
        this.player = player;
        this.type = type;
        this.content = content;
    }

    /** @return The player who joined/left/transferred. */
    public Player getPlayer() {
        return this.player;
    }

    /** @return The {@link Type} of this event. */
    public Type getType() {
        return this.type;
    }

    /** @return A {@link String} containing the pre-formatted message content. */
    public String getContent() {
        return this.content;
    }
}
