package com.dumbdogdiner.stickychat.api;

import com.dumbdogdiner.stickychat.api.misc.SignNotification;
import com.dumbdogdiner.stickychat.api.util.WithPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * An interface for chat formatting.
 */
public interface Formatter extends WithPlayer {
    /**
     * Colorize a string using `&` color codes.
     * @param string The string to colorize
     * @return {@link String}
     */
    static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Format a message sent to the entire server.
     *
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatMessage(@NotNull String message);

    /**
     * Format a message sent in staff chat.
     *
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatStaffChatMessage(@NotNull String message);

    /**
     * Format an incoming direct message sent between two players.
     *
     * @param to The recipient of the message
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatOutgoingDM(@NotNull Player to, @NotNull String message);

    /**
     * Format a direct message sent between two players.
     *
     * @param from The sender of the message
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatIncomingDM(@NotNull Player from, @NotNull String message);

    /**
     * Format a SignSpy notification.
     *
     * @param notification - The notification to format
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatSignSpyNotification(@NotNull SignNotification notification);
}
