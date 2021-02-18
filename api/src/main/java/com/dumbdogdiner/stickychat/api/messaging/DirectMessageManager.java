package com.dumbdogdiner.stickychat.api.messaging;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Manages direct messages.
 */
public interface DirectMessageManager {
    /**
     * Send a DM to the target player.
     *
     * @param from The player who is sending a message
     * @param to The player receiving the message
     * @param message The message to send
     * @return {@link DirectMessageResult}
     */
    @NotNull DirectMessageResult sendMessage(@NotNull Player from, @NotNull Player to, @NotNull String message);

    /**
     * Gets the last player the target player sent a message to.
     * @return {@link Player}
     */
    @Nullable Player getLast(@NotNull Player player);

    /**
     * Set the last player this player sent a message to.
     */
    void setLastPlayer(@NotNull Player player);

    /**
     * Send a DM to the last player who this player sent a message to, or received a
     * message from. Returns true if the message was sent successfully.
     *
     * @param player The player sending the message
     * @param message The message to send
     * @return {@link Boolean}
     */
    @NotNull DirectMessageResult sendToLast(Player player, @NotNull String message);

    /**
     * Block the target player. Returns false if the player is already blocked.
     *
     * @param target The player to block
     * @return {@link Boolean}
     */
    @NotNull Boolean block(@NotNull Player target);

    /**
     * Unblock the target player. Returns false if the player is not blocked.
     *
     * @param target The player to unblock
     * @return {@link Boolean}
     */
    @NotNull Boolean unblock(@NotNull Player target);

    /**
     * Send this player a system message.
     *
     * @param message The message to send
     * @return {@link DirectMessageResult}
     */
    default @NotNull DirectMessageResult sendSystemMessage(@NotNull Player player, @NotNull String message) {
        return this.sendSystemMessage(player, new TextComponent(message));
    }

    /**
     * Send this player a system
     *
     * @param message The message to send
     * @return {@link DirectMessageResult}
     */
    @NotNull DirectMessageResult sendSystemMessage(@NotNull Player player, @NotNull BaseComponent message);

    /**
     * @return A {@link Collection} of {@link Player}s who have direct messages disabled.
     */
    Collection<Player> getDisabledPlayers();

    /**
     * Test if the target player has direct messages disabled.
     * @param player The target player
     * @return True if the player has direct messages disabled.
     */
    Boolean hasDirectMessagesDisabled(Player player);

    /**
     * Enable direct messages for the target player.
     * @param player The target player
     */
    void enableDirectMessages(Player player);

    /**
     * Disable direct messages for the target player
     * @param player The target player
     */
    void disableDirectMessages(Player player);
}
