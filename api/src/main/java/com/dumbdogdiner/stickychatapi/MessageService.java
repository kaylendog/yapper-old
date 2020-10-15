package com.dumbdogdiner.stickychatapi;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the sending of
 * direct messages to other players for a specific player.
 * Classes implementing this interface should instantiate new
 * instances for each player who requests a direct message.
 * These should be cached, and returned when access is requested.
 */
public interface MessageService {
    /**
     * Get the player this service refers to.
     *
     * @return {@link Player}
     */
    Player getPlayer();

    /**
     * Get the recipients of a message, given the sender and its priority. Returns
     * a list of recipients.
     *
     * @param from The sender of the message
     * @param priority The priority of the message
     * @return {@link List}
     */
    static List<Player> getRecipients(Player from, Priority priority) {
        var recipients = new ArrayList<Player>();
        StickyChat.getService().getDataServices().forEach((data) -> {
            if (priority.isGreaterThan(data.getPriority()) && !data.getBlocked(from)) {
                recipients.add(data.getPlayer());
            }
        });
        return recipients;
    }

    /**
     * Send a message to global chat. This method should check if the player
     * can send messages when invoked.
     *
     * @param message The message to send
     * @return {@link MessageResult}
     */
    MessageResult broadcast(String message);

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
