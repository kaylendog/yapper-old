/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messaging;

import com.dumbdogdiner.stickychat.api.signspy.SignNotification;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** An interface for chat formatting. */
public interface Formatter {
    /** Regular expression used for formatting hexadecimal color codes. */
    Pattern COLOR_FORMATTING_REGEX = Pattern.compile(
            "(?<formatting>&&?(?:#[a-f0-9]{6}|[a-f0-9k-or]))?(?<content>.*?)(?=(&&?(?:#[a-f0-9]{6}|[a-f0-9k-or]))|$)",
            Pattern.MULTILINE);

    /**
     * Colorize a text component using Minecraft hex codes.
     *
     * @param component The component to colorize
     * @return {@link TextComponent}
     */
    static @NotNull TextComponent formatHexCodes(@NotNull TextComponent component) {
        return Formatter.formatHexCodes(component.getText());
    }

    /**
     * Colorize a string using Minecraft hex codes.
     *
     * @param message The message to colorize
     * @return {@link TextComponent}
     */
    static @NotNull TextComponent formatHexCodes(String message) {
        var matcher = COLOR_FORMATTING_REGEX.matcher(message);
        var magic = false;
        var bold = false;
        var strike = false;
        var underline = false;
        var italic = false;
        ChatColor color = null;
        var rootComponent = new TextComponent();
        var nextComponent = new TextComponent();
        while (matcher.find()) {
            boolean escaped = false;
            if (matcher.group("formatting") != null) {
                var format = matcher.group("formatting");
                if (format.startsWith("&&")) {
                    escaped = true;
                } else if (format.charAt(1) == '#') {
                    color = ChatColor.of(format.substring(1));
                } else {
                    switch (format.charAt(1)) {
                        case 'x', 'X' -> magic = true;
                        case 'l', 'L' -> bold = true;
                        case 'm', 'M' -> strike = true;
                        case 'n', 'N' -> underline = true;
                        case 'o', 'O' -> italic = true;
                        case 'r', 'R' -> {
                            magic = false;
                            bold = false;
                            strike = false;
                            underline = false;
                            italic = false;
                            color = ChatColor.WHITE;
                        }
                        default -> color = ChatColor.getByChar(format.charAt(1));
                    }
                }
            }
            if (matcher.group("content") == null) {
                continue;
            }
            nextComponent.setText((escaped ? matcher.group("formatting").substring(1) : "") + matcher.group("content"));
            nextComponent.setObfuscated(magic);
            nextComponent.setBold(bold);
            nextComponent.setStrikethrough(strike);
            nextComponent.setUnderlined(underline);
            nextComponent.setItalic(italic);
            nextComponent.setColor(color);
            rootComponent.addExtra(nextComponent);
            // reset component
            nextComponent = new TextComponent();
            magic = false;
            bold = false;
            strike = false;
            underline = false;
            italic = false;
        }
        return rootComponent;
    }

    /**
     * Format a message sent to the entire server.
     *
     * @param player The player to format as
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatMessage(Player player, @NotNull String message);

    /**
     * Format a message sent in staff chat.
     *
     * @param player The player to format as
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatStaffChatMessage(Player player, @NotNull String message);

    /**
     * Format an incoming direct message sent between two players.
     *
     * @param from The player to format as
     * @param to The recipient of the message
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatOutgoingDM(@NotNull Player from, @NotNull Player to, @NotNull String message);

    /**
     * Format a direct message sent between two players.
     *
     * @param from The sender of the message
     * @param to The recipient of the message
     * @param message The message
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatIncomingDM(@NotNull Player from, @NotNull Player to, @NotNull String message);

    /**
     * Format a SignSpy notification.
     *
     * @param notification - The notification to format
     * @return {@link BaseComponent}
     */
    @NotNull
    BaseComponent formatSignSpyNotification(@NotNull SignNotification notification);
}
