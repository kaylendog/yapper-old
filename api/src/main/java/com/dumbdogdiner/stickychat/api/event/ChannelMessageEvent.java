package com.dumbdogdiner.stickychat.api.event;

import com.dumbdogdiner.stickychat.api.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.awt.TextComponent;
import java.util.List;

/**
 * An event fired when a player sends a message in a channel.
 */
public class ChannelMessageEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Channel channel;
    private final Player sender;
    private final List<Player> recipients;
    private final TextComponent content;

    private boolean cancelled;

    public ChannelMessageEvent(
            @NotNull Channel channel,
            @NotNull Player sender,
            @NotNull List<Player> recipients,
            @NotNull TextComponent content
    ) {
        this.channel = channel;
        this.sender = sender;
        this.recipients = recipients;
        this.content = content;
    }

    /**
     * Get the channel the message was sent in.
     * @return The {@link Channel} the message was sent in.
     */
    public Channel getChannel() {
        return this.channel;
    }

    /**
     * Get the player who sent the message.
     * @return The {@link Player} who sent the message.
     */
    public Player getSender() {
        return this.sender;
    }

    /**
     * Get a list of players who received the message. This is not necessarily
     * the list of players in this channel.
     * @return A {@link List<Player>} of recipients.
     */
    public List<Player> getRecipients() {
        return this.recipients;
    }

    /**
     * Get the content of this message.
     * @return The {@link TextComponent} containing the message content.
     */
    public TextComponent getContent() {
        return this.content;
    }

    /**
     * @return True if this event is cancelled.
     */
    public @Override boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Change the cancelled state of this event.
     * @param cancel The new state
     */
    public @Override void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }


    public @NotNull @Override HandlerList getHandlers() {
        return handlers;
    }
}
