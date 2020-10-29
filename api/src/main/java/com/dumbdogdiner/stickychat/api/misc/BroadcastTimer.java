package com.dumbdogdiner.stickychat.api.misc;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Handles the storage of data for broadcasts.
 */
public class BroadcastTimer {
    /**
     * Create a broadcast timer from
     * @param text
     * @return
     */
    public BroadcastTimer fromText(@NotNull String text) {
        var component = new TextComponent();
        component.setText(text);
        return new BroadcastTimer(component);
    }

    @NotNull
    BaseComponent component;

    /**
     * Construct a new timer with the given component.
     *
     * @param component The component belonging to this timer.
     */
    public BroadcastTimer(@NotNull BaseComponent component) {
        this.component = component;
    }

}
