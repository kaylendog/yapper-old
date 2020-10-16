package com.dumbdogdiner.stickychatapi.misc;

/**
 * Manages timed broadcasts and MOTDs.
 */
public interface BroadcastService {
    /**
     * Register a new {@link BroadcastTimer}. Returns the ID of the
     * registered timer.
     *
     * @param timer The timer to register
     * @return {@link Integer}
     */
    Integer registerTimer(BroadcastTimer timer);

    /**
     * Get the timer with the specified ID.
     * @param id The ID of the timer
     * @return {@link BroadcastTimer}
     */
    BroadcastTimer getTimer(Integer id);

    /**
     * Check if the target timer is registered.
     *
     * @param timer The timer to check
     * @return {@link Boolean}
     */
    Boolean isRegistered(BroadcastTimer timer);

    /**
     * Trigger the timer with the specified ID.
     *
     * @param id The ID of the timer to trigger
     */
    default void triggerTimer(Integer id) {
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
    void triggerTimer(BroadcastTimer timer);
}
