package com.dumbdogdiner.stickychatapi;

import org.bukkit.entity.Player;

/**
 * Manages the sending of direct messages to other players for
 * a specific player. Classes implementing this interface should
 * instantiate new instances for each player who requests a direct
 * message. These should be cached, and returned when access is
 * requested. When sending messages to players
 */
public interface DirectMessageService {
    /**
     * Send a DM to the target player. Calling this method should invoke
     * {@link DirectMessageService#receive(Player, String)} of the DM
     * service of the target player.
     *
     * @param target The player receiving the message
     * @param message The message to send
     * @return {@link java.lang.Boolean}
     */
    DirectMessageResult send(Player target, String message);

    /**
     * Send a DM to the last player who this player sent a message to, or received a
     * message from. Returns true if the message was sent successfully.
     *
     * @param message The message to send
     * @return {@link java.lang.Boolean}
     */
    DirectMessageResult sendToLast(String message);

    /**
     * Handle a received DM from the specified player. Returns a direct message
     * result.
     *
     * @param from The player who sent the message
     * @param message The message being sent
     * @return {@link DirectMessageResult}
     */
    DirectMessageResult receive(Player from, String message);

    /**
     * Block the target player. Returns false if the player is already blocked.
     *
     * @param target The player to block
     * @return {@link java.lang.Boolean}
     */
    Boolean block(Player target);

    /**
     * Unblock the target player. Returns false if the player is not blocked.
     *
     * @param target The player to unblock
     * @return {@link java.lang.Boolean}
     */
    Boolean unblock(Player target);
}
