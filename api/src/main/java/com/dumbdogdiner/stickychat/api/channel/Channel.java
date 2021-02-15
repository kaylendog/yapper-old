package com.dumbdogdiner.stickychat.api.channel;

import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Represents a channel in which players can send messages.
 */
public interface Channel {
    /**
     * Deserialize a configuration section into a channel object.
     *
     * @param key The key of the config section
     * @param section The section to deserialize
     * @return {@link Channel}
     */
    static @NotNull Channel deserialize(@NotNull String key, @NotNull ConfigurationSection section) {
        var type = ChannelType.valueOf(section.getString("type"));
        var name = section.getString("name");
        return StickyChat.getService().getChannelManager().restoreChannel(UUID.fromString(key), type, name);
    }

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
     * @return {@link List<Player>}
     */
    @NotNull List<Player> getPlayers();

    /**
     * Add a player to this channel. Implementations should also update the data service.
     *
     * @param player The player to add
     * @return {@link Boolean}
     */
    @NotNull Boolean addPlayer(@NotNull Player player);

    /**
     * Remove a player from this channel.
     *
     * @param player The player to remove
     * @return {@link Boolean}
     */
    @NotNull Boolean removePlayer(@NotNull Player player);

    /**
     * Send a message to this channel.
     *
     * @param from The player this message was from
     */
    default void sendToChannel(Player from) {

    }

    /**
     * Close this channel and move all players to global.
     */
    void close();

    /**
     * Serialize this channel into a YAML configuration section.
     *
     * @param config The configuration this channel is being serialized into
     * @return {@link ConfigurationSection}
     */

    default @NotNull ConfigurationSection serialize(@NotNull Configuration config) {
        var section = config.createSection(this.getName());
        section.set("type", this.getType().name());
        return section;
    }
}
