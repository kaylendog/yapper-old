package com.dumbdogdiner.stickychat.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/** Represents a generic event for StickyChat. */
class ChatEvent extends Event implements Cancellable {
    /** An array of handlers that are listening for this event. */
    private static final HandlerList handlers = new HandlerList();
    /** Whether this event has been cancelled.a */
    private boolean cancelled = false;

    /** @return True if this event is cancelled. */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Change the cancelled state of this event.
     *
     * @param cancel The new state
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    /** @return A list of handlers for this event. */
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    /** @return A list of handlers for this event. */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
