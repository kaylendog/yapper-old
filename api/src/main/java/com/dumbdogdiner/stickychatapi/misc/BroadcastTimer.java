package com.dumbdogdiner.stickychatapi.misc;

import net.md_5.bungee.api.chat.BaseComponent;

/**
 * Handles the storage of data for broadcasts.
 */
public class BroadcastTimer {
    BaseComponent component;

    /**
     * Construct a new timer with the given component.
     *
     * @param component The component belonging to this timer.
     */
    public BroadcastTimer(BaseComponent component) {
        this.component = component;
    }
}
