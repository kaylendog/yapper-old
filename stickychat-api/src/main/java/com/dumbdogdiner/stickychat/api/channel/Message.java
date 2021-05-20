package com.dumbdogdiner.stickychat.api.channel;

import java.awt.TextComponent;
import java.util.List;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** Represents a message sent in a channel. */
public interface Message {
    /**
     * Get the player who sent the message.
     *
     * @return The {@link Player} who sent the message.
     */
    @NotNull
    Player getSender();

    /**
     * Get the content of this message.
     *
     * @return A {@link TextComponent} containing the message content.
     */
    @NotNull
    TextComponent getContent();

    /**
     * Get a list of players who received this message.
     *
     * @return A {@link List} of players who received this message.
     */
    @NotNull
    List<Player> getRecipients();
}
