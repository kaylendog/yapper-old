package com.dumbdogdiner.stickychat.api.player;

import com.dumbdogdiner.stickychat.api.result.MessageResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Manages the sending of messages to other players for a specific player.
 */
public interface MessageManager {
    /**
     * Send a message to global chat. This method should check if the player
     * can send messages when invoked.
     *
     * @param player  The player sending the message
     * @param message The message to send
     * @return {@link MessageResult}
     */
    @NotNull MessageResult sendMessage(@NotNull Player player, @NotNull String message);
}
