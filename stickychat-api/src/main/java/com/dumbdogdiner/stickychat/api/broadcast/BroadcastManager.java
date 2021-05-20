package com.dumbdogdiner.stickychat.api.broadcast;



import org.jetbrains.annotations.NotNull;

/** Manages timed broadcasts and MOTDs. */
public interface BroadcastManager {
    /**
     * Register a new {@link BroadcastTimer}. Returns the ID of the registered timer.
     *
     * @param timer The timer to register
     * @return {@link Integer}
     */
    @NotNull
    Integer registerTimer(@NotNull BroadcastTimer timer);

    /**
     * Check if the target timer is registered.
     *
     * @param timer The timer to check
     * @return {@link Boolean}
     */
    @NotNull
    Boolean isRegistered(@NotNull BroadcastTimer timer);

    /**
     * Trigger the specified timer.
     *
     * @param timer The timer to trigger
     */
    void triggerTimer(@NotNull BroadcastTimer timer);
}
