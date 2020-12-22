package com.dumbdogdiner.stickychat.api.chat;

import com.dumbdogdiner.stickychat.api.util.WithPlayer;
import com.dumbdogdiner.stickychat.api.result.MessageResult;
import com.dumbdogdiner.stickychat.api.result.MuteReason;
import com.dumbdogdiner.stickychat.api.Priority;
import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the sending of messages to other players for a specific player.
 * Classes implementing this interface should instantiate new
 * instances for each player who requests to send a message.
 * These should be cached, and returned when access is requested.
 */
public interface MessageService extends WithPlayer {
    /**
     * Get the player this service refers to.
     *
     * @return {@link Player}
     */
    @NotNull
    Player getPlayer();

    /**
     * Get the recipients of a message, given the sender and its priority. Returns
     * a list of recipients.
     *
     * @param from The sender of the message
     * @param priority The priority of the message
     * @return {@link List}
     */
    @NotNull
    static List<Player> getRecipients(@NotNull Player from, @NotNull Priority priority) {
        var recipients = new ArrayList<Player>();
        StickyChat.getService().getDataServices().forEach((data) -> {
            if (priority.isGreaterThan(data.getPriority()) && !data.getBlocked(from)) {
                recipients.add(data.getPlayer());
            }
        });
        recipients.add(from);
        return recipients;
    }

    /**
     * Return the channel this player is currently in.
     *
     * @return {@link Channel}
     */
    @NotNull
    Channel getChannel();

    /**
     * Set the channel for this player.
     *
     * @param channel The channel to move to
     * @return {@link Boolean}
     */
    @NotNull
    Boolean moveChannel(@NotNull Channel channel);

    /**
     * Send a message to global chat. This method should check if the player
     * can send messages when invoked.
     *
     * @param message The message to send
     * @return {@link MessageResult}
     */
    @NotNull
    MessageResult broadcast(@NotNull String message);

    /**
     * Mute this player. This assumes the mute reason is {@link MuteReason#DEFAULT}.
     * Returns false if this player is already muted.
     *
     * @return {@link Boolean}
     */
    @NotNull
    Boolean mute();

    /**
     * Mute this player, providing the specified reason. Returns false if
     * this player is already muted.
     *
     * @param reason The reason for this mute
     * @return {@link Boolean}
     */
    @NotNull
    Boolean mute(@NotNull MuteReason reason);

    /**
     * Unmute this player. Returns false if they are not muted.
     *
     * @return {@link Boolean}
     */
    @NotNull
    Boolean unmute();
}
