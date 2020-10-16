package com.dumbdogdiner.stickychatapi.misc;

/**
 * Manages timed broadcasts and MOTDs.
 */
public interface BroadcastService {
    /**
     * Register a new {@link BroadcastTimer}.
     *
     * @param timer The timer to register
     */
    void registerTimer(BroadcastTimer timer);
}
