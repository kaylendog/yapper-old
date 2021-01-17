package com.dumbdogdiner.stickychat.api.chat;

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
     * Enum of possible channel types.
     */
    public enum Type {
        /**
         * The default channel type - used by plugin implementations.
         */
        DEFAULT,

        /**
         * The global channel - there should only be one of these channels.
         */
        GLOBAL,

        /**
         * A group DM channel - there can be many instances of this type.
         */
        GROUP_DM,

        /**
         * A staff chat channel - there can be many instances of this type.
         */
        STAFF_CHAT
    }

    /**
     * Deserialize a configuration section into a channel object.
     *
     * @param key The key of the config section
     * @param section The section to deserialize
     * @return {@link Channel}
     */
    @NotNull
    static Channel deserialize(@NotNull String key, @NotNull ConfigurationSection section) {
        var type = Type.valueOf(section.getString("type"));
        var name = section.getString("name");
        return StickyChat.getService().getChannelManager().restoreChannel(UUID.fromString(key), type, name);
    }

    /**
     * Get the channel manager this channel belongs to.
     *
     * @return {@link ChannelManager}
     */
    @NotNull
    public default ChannelManager getManager() {
        return StickyChat.getService().getChannelManager();
    }

    /**
     * Return the unique ID for this channel.
     *
     * @return {@link UUID}
     */
    @NotNull
    public UUID getUniqueId();

    /**
     * Return the type of this channel.
     *
     * @return {@link Type}
     */
    @NotNull
    public Type getType();

    /**
     * Set the type of this channel. Implementations should not allow more than one global, or staff chat channel instance.
     *
     * @param type The new type
     */
    public void setType(@NotNull Type type);

    /**
     * Get the name of this channel.
     *
     * @return {@link String}
     */
    @NotNull
    public String getName();

    /**
     * Set the name of this channel.
     *
     * @param name The new name of this channel
     */
    public void setName(@NotNull String name);

    /**
     * Get a list of players in this channel.
     *
     * @return {@link List<Player>}
     */
    @NotNull
    public List<Player> getPlayers();

    /**
     * Add a player to this channel. Implementations should also update the data service.
     *
     * @param player The player to add
     * @return {@link Boolean}
     */
    @NotNull
    public Boolean addPlayer(@NotNull Player player);

    /**
     * Remove a player from this channel.
     *
     * @param player The player to remove
     * @return {@link Boolean}
     */
    @NotNull
    public Boolean removePlayer(@NotNull Player player);

    /**
     * Send a message to this channel.
     *
     * @param from The player this message was from
     */
    public default void sendToChannel(Player from) {

    }

    /**
     * Close this channel and move all players to global.
     */
    public void close();

    /**
     * Serialize this channel into a YAML configuration section.
     *
     * @param config The configuration this channel is being serialized into
     * @return {@link ConfigurationSection}
     */
    @NotNull
    public default ConfigurationSection serialize(@NotNull Configuration config) {
        var section = config.createSection(this.getUniqueId().toString());
        section.set("type", this.getType().name());
        return section;
    }
}
