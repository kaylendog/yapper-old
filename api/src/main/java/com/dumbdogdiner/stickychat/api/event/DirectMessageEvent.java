package com.dumbdogdiner.stickychat.api.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An event that fires when a player is sending a direct message.
 */
public final class DirectMessageEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player sender;
    private Player recipient;
    private final String recipientName;
    private final String content;

    private boolean cancelled = false;
    private String cancelReason;

    /**
     * Construct a direct message event for an intra-server message.
     * @param sender The sender
     * @param recipient The target recipient
     * @param content The message content
     */
    public DirectMessageEvent(Player sender, Player recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.recipientName = recipient.getName();
        this.content = content;
    }

    /**
     * Construct a direct message event for an inter-server message.
     * @param sender The sender
     * @param recipientName The name of the target recipient
     * @param content The message content
     */
    public DirectMessageEvent(Player sender, String recipientName, String content) {
        this.sender = sender;
        this.recipientName = recipientName;
        this.content = content;
    }

    /**
     * @return The {@link String} containing the content of this message.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @return The {@link Player} who sent this message.
     */
    public Player getSender() {
        return this.sender;
    }

    /**
     * @return The {@link Player} who will receive this message.
     */
    public String getRecipientName() {
        return this.recipientName;
    }

    /**
     * Get the recipient of this message.
     * @return The recipient, or null if they are offline or on a remote server.
     */
    @Nullable
    public Player getRecipient() {
        if (this.recipient != null && this.recipient.isOnline()) {
            return this.recipient;
        }
        return Bukkit.getPlayerExact(this.recipientName);
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return True if this event is cancelled.
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Change the cancelled state of this event.
     * @param cancel The new state
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public void setCancelReason(String cancel) {
        this.cancelReason = cancel;
    }

    public String getCancelReason() {
        return this.cancelReason;
    }
}
