package com.dumbdogdiner.stickychat.api.player;

import com.dumbdogdiner.stickychat.api.Priority;
import com.dumbdogdiner.stickychat.api.StickyChat;
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageResult;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A utility interface for accessing player data quickly.
 */
public interface ChatPlayer {
    /**
     * Get the player this chat player wraps.
     *
     * @return A {@link Player}.
     */
    @NotNull Player getPlayer();

    /**
     * Get the priority level of this player.
     *
     * @return The player's current {@link Priority}
     */
    @NotNull default Priority getPriority() {
        return StickyChat.getService().getPriorityManager().getPriority(this.getPlayer());
    }

    /**
     * Send this player a direct message.
     *
     * @param message The message to send
     * @return A {@link DirectMessageResult}, used to determine the success of this action.
     */
    @NotNull DirectMessageResult sendDirectMessage(TextComponent message);
}
