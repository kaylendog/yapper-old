package com.dumbdogdiner.stickychat.api.chat;


import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

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
     * Return the unique ID for this channel.
     *
     * @return {@link UUID}
     */
    public UUID getUniqueId();

    /**
     * Set the type of this channel. Implementations should not allow more than one global, or staff chat channel instance.
     *
     * @param type
     */
    public void setType(Type type);

    /**
     * Get a list of players in this channel.
     *
     * @return {@link List<Player>}
     */
    public List<Player> getPlayers();

    /**
     * Add a player to this channel. Implementations should also update the data service.
     *
     * @param player The player to add
     * @return {@link Boolean}
     */
    public Boolean addPlayer(Player player);

    /**
     * Remove a player from this channel.
     *
     * @param player The player to remove
     * @return {@link Boolean}
     */
    public Boolean removePlayer(Player player);

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
}
