package com.dumbdogdiner.stickychat.api.chat;

import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Manages the creation and destruction of channels.
 */
public interface ChannelManager {
    /**
     * Create a new channel of the specified type.
     *
     * @param type The type of the channel
     * @return {@link Channel}
     */
    public Channel createChannel(Channel.Type type);

    /**
     * Remove the channel with the target UUID.
     *
     * @param id
     */
    public void removeChannel(UUID id);

    /**
     * Get the channel with the target UUID.
     *
     * @param id The UUID of the channel
     * @return {@link Channel}
     */
    public Channel getChannel(UUID id);

    /**
     * Get the channel for the target player.
     *
     * @param player The target player
     * @return {@link Channel}
     */
    default public Channel getChannel(Player player) {
        return StickyChat.getService().getChannelService(player).getChannel();
    }

    /**
     * Get a list of all channels.
     *
     * @return {@link List<Channel>}
     */
    public List<Channel> getChannels();
}
