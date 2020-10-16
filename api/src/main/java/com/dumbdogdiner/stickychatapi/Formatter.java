package com.dumbdogdiner.stickychatapi;

import com.dumbdogdiner.stickychatapi.misc.SignNotification;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

/**
 * An interface for chat formatting.
 */
public interface Formatter {
    /**
     * Format a message sent to the entire server.
     *
     * @param message The message
     * @return {@link String}
     */
    String formatMessage(String message);

    /**
     * Format a message sent in staff chat.
     *
     * @param message The message
     * @return {@link String}
     */
    String formatStaffChatMessage(String message);

    /**
     * Format an incoming direct message sent between two players.
     *
     * @param to The recipient of the message
     * @param message The message
     * @return {@link String}
     */
    String formatOutgoingDM(Player to, String message);

    /**
     * Format a direct message sent between two players.
     *
     * @param from The sender of the message
     * @param message The message
     * @return {@link String}
     */
    String formatIncomingDM(Player from, String message);

    /**
     * Format a SignSpy notification.
     *
     * @param notification - The notification to format
     * @return {@link BaseComponent}
     */
    BaseComponent formatSignSpyNotification(SignNotification notification);
}
