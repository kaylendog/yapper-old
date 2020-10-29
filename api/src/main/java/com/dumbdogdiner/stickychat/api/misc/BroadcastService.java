package com.dumbdogdiner.stickychat.api.misc;

import org.jetbrains.annotations.NotNull;

/**
 * Manages timed broadcasts and MOTDs.
 */
public interface BroadcastService {
    /**
     * Load all the timers from plugin configuration. Return the
     * number of timers that were loaded.
     *
     * @return {@link Integer}
     */
    Integer loadAllTimers();

    /**
     * Register a new {@link BroadcastTimer}. Returns the ID of the
     * registered timer.
     *
     * @param timer The timer to register
     * @return {@link Integer}
     */
    @NotNull
    Integer registerTimer(@NotNull BroadcastTimer timer);

    /**
     * Get the timer with the specified ID.
     * @param id The ID of the timer
     * @return {@link BroadcastTimer}
     */
    BroadcastTimer getTimer(@NotNull Integer id);

    /**
     * Check if the target timer is registered.
     *
     * @param timer The timer to check
     * @return {@link Boolean}
     */
    @NotNull
    Boolean isRegistered(@NotNull BroadcastTimer timer);

    /**
     * Trigger the timer with the specified ID.
     *
     * @param id The ID of the timer to trigger
     */
    default void triggerTimer(@NotNull Integer id) {
        var timer = getTimer(id);
        if (timer == null) {
            throw new RuntimeException("Attempted to trigger unregistered timer.");
        }
        triggerTimer(timer);
    }

    /**
     * Trigger the specified timer.
     *
     * @param timer The timer to trigger
     */
    void triggerTimer(@NotNull BroadcastTimer timer);
}
