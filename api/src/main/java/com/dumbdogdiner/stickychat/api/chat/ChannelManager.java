package com.dumbdogdiner.stickychat.api.chat;

import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * @param name THe name of the channel
     * @return {@link Channel}
     */
    @NotNull
    public Channel createChannel(@NotNull Channel.Type type, @NotNull String name);

    /**
     * Restore a channel.
     *
     * @param uuid The unique id of this channel
     * @param type The type of this channel
     * @param name The name of this channel
     * @return
     */
    @NotNull
    public Channel restoreChannel(@NotNull UUID uuid, @NotNull Channel.Type type, @NotNull String name);

    /**
     * Get the global channel all players can talk in.
     * @return
     */
    public Channel getGlobalChannel();

    /**
     * Remove the channel with the target UUID. Returns true if the target channel
     * was removed.
     *
     * @param id
     * @return {@link Boolean}
     */
    public Boolean removeChannel(UUID id);

    /**
     * Get the channel with the target UUID.
     *
     * @param id The UUID of the channel
     * @return {@link Channel}
     */
    @Nullable
    public Channel getChannel(@NotNull UUID id);

    /**
     * Get the channel for the target player.
     *
     * @param player The target player
     * @return {@link Channel}
     */
    @NotNull
    default public Channel getChannel(@NotNull Player player) {
        return StickyChat.getService().getMessageService(player).getChannel();
    }

    /**
     * Get a list of all channels.
     *
     * @return {@link List<Channel>}
     */
    @NotNull
    public List<Channel> getChannels();
}
