package com.dumbdogdiner.stickychat.api.player;

import com.dumbdogdiner.stickychat.api.messaging.DirectMessageResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ChatPlayer {
    /**
     * Get the player this chat player wraps.
     * @return A {@link Player}.
     */
    @NotNull Player getPlayer();

    /**
     * Send this player a direct message.
     * @return A {@link DirectMessageResult}, used to determine the success of this action.
     */
    @NotNull DirectMessageResult sendDirectMessage();
}
