package com.dumbdogdiner.stickychat.api.broadcast;

import net.md_5.bungee.api.chat.TextComponent;

/**
 * Handles the storage of data for broadcasts.
 */
public interface BroadcastTimer {
    /**
     * @return The {@link TextComponent} that this timer wraps.
     */
    TextComponent getContent();

    /**
     * @return The interval in seconds between timer broadcasts.
     */
    Long getInterval();
}
