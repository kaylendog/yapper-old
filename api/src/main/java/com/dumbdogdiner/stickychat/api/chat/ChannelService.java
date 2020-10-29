package com.dumbdogdiner.stickychat.api.chat;

import com.dumbdogdiner.stickychat.api.util.WithPlayer;

/**
 * Manages a player's current channel.
 */
public interface ChannelService extends WithPlayer {
    /**
     * Return the channel this player is currently in.
     *
     * @return {@link Channel}
     */
    Channel getChannel();

    /**
     * Set the channel for this player.
     *
     * @param channel The channel to move to
     * @return {@link Boolean}
     */
    Boolean moveChannel(Channel channel);
}
