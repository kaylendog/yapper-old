package com.dumbdogdiner.stickychatapi;

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
     * Mute this player. This assumes the mute reason is {@link MuteReason#DEFAULT}.
     * Returns false if this player is already muted.
     *
     * @return {@link Boolean}
     */
    Boolean mute();

    /**
     * Mute this player, providing the specified reason. Returns false if
     * this player is already muted.
     *
     * @param reason The reason for this mute
     * @return {@link Boolean}
     */
    Boolean mute(MuteReason reason);

    /**
     * Unmute this player. Returns false if they are not muted.
     *
     * @return {@link Boolean}
     */
    Boolean unmute();
}
