package com.dumbdogdiner.stickychat.api.channel;

import org.bukkit.configuration.ConfigurationSection;
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
    @NotNull Channel createChannel(@NotNull ChannelType type, @NotNull String name);

    /**
     * @return The global channel all players can talk in.
     */
    default @NotNull Channel getGlobalChannel() {
        var channel = this.getChannel(new UUID(0, 0));
        assert channel != null;
        return channel;
    }

    /**
     * Remove the channel with the target UUID. Returns true if the target channel
     * was removed.
     *
     * @param id The UUID of the
     * @return {@link Boolean}
     */
    Boolean removeChannel(UUID id);

    /**
     * Get the channel with the target UUID.
     *
     * @param id The UUID of the channel
     * @return {@link Channel}
     */
    @Nullable Channel getChannel(@NotNull UUID id);

    /**
     * Get the channel of the target player.
     * @param player The target player
     * @return The current {@link Channel} the player is in.
     */
    @NotNull List<Channel> getPlayerChannels(@NotNull Player player);

    /**
     * Get a list of all channels.
     *
     * @return A {@link List} of {@link Channel}s
     */
    @NotNull List<Channel> getChannels();
}
