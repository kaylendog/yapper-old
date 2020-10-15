package com.dumbdogdiner.stickychatapi;

import org.bukkit.entity.Player;

/**
 * Manages the sending of
 * direct messages to other players for a specific player.
 * Classes implementing this interface should instantiate new
 * instances for each player who requests a direct message.
 * These should be cached, and returned when access is requested.
 */
public interface MessageService {
    /**
     * Send a message. This method should check if the player
     * can send messages when invoked.
     *
     * @param message The message to send
     * @return {@link MessageResult}
     */
    MessageResult send(String message);

    /**
     * Mute this player. Returns false if they are already muted.
     *
     * @return {@link Boolean}
     */
    Boolean mute();

    /**
     * Unmute this player. Returns false if they are not muted.
     *
     * @return {@link Boolean}
     */
    Boolean unmute();
}
