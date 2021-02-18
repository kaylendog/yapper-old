package com.dumbdogdiner.stickychat.api.channel;

import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.TextComponent;
import java.util.List;

/**
 * Represents a channel in which players can send messages.
 */
public interface Channel {
    /**
     * Get the channel manager this channel belongs to.
     *
     * @return {@link ChannelManager}
     */
    default @NotNull ChannelManager getManager() {
        return StickyChat.getService().getChannelManager();
    }

    /**
     * Return the type of this channel.
     *
     * @return {@link ChannelType}
     */
    @NotNull ChannelType getType();

    /**
     * Get the name of this channel.
     *
     * @return {@link String}
     */
    @NotNull String getName();

    /**
     * Set the name of this channel.
     *
     * @param name The new name of this channel
     */
    void setName(@NotNull String name);

    /**
     * Get a list of players in this channel.
     *
     * @return A {@link List} of {@link Player}s in the channel
     */
    @NotNull List<Player> getPlayers();

    /**
     * Add a player to this channel. Implementations should also update the data service.
     *
     * @param player The player to add
     * @return True if the player was added, false if they are already in this channel.
     */
    @NotNull Boolean addPlayer(@NotNull Player player);

    /**
     * Remove a player from this channel.
     *
     * @param player The player to remove
     * @return True if the player was removed, false if they aren't in the channel
     */
    @NotNull Boolean removePlayer(@NotNull Player player);

    /**
     * Send a message to this channel.
     *
     * @param from The player this message was from
     * @param message The message to send
     * @return A {@link MessageResult}
     */
    @NotNull MessageResult send(Player from, TextComponent message);

    /**
     * Close this channel and move all players to global.
     */
    void close();

    /**
     * Serialize this channel into a YAML configuration section.
     *
     * @param config The configuration this channel is being serialized into
     * @return The modified {@link ConfigurationSection}
     */
    @NotNull ConfigurationSection serialize(@NotNull Configuration config);
}
