package com.dumbdogdiner.stickychatapi.misc;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Handles the storage of data for broadcasts.
 */
public class BroadcastTimer {
    public BroadcastTimer fromText(String text) {
        var component = new TextComponent();
        component.setText(text);
        return new BroadcastTimer(component);
    }

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
