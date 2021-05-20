package com.dumbdogdiner.stickychat.api.broadcast;



import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/** Handles the storage of data for broadcasts. */
public interface BroadcastTimer {
    /** @return The {@link TextComponent} that this timer wraps. */
    @NotNull
    TextComponent getContent();

    /** @return The interval in seconds between timer broadcasts. */
    long getInterval();

    /** @return The delay in seconds after which the timer should run. */
    long getDelay();

    /** @return The number of times this broadcast should trigger. */
    int getMaxCount();
}
