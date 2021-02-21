package com.dumbdogdiner.stickychat.api.broadcast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Manages timed broadcasts and MOTDs.
 */
public interface BroadcastManager {
    /**
     * Load all the timers from plugin configuration. Return the
     * number of timers that were loaded.
     *
     * @return {@link Integer}
     */
    Integer loadTimers();

    /**
     * Register a new {@link BroadcastTimer}. Returns the ID of the
     * registered timer.
     *
     * @param timer The timer to register
     * @return {@link Integer}
     */
    @NotNull Integer registerTimer(@NotNull BroadcastTimer timer);

    /**
     * Check if the target timer is registered.
     *
     * @param timer The timer to check
     * @return {@link Boolean}
     */
    @NotNull Boolean isRegistered(@NotNull BroadcastTimer timer);

    /**
     * Trigger the specified timer.
     *
     * @param timer The timer to trigger
     */
    void triggerTimer(@NotNull BroadcastTimer timer);
}
