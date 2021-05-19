package com.dumbdogdiner.stickychat.api.integration;

import com.dumbdogdiner.stickychat.api.messaging.Formatter;
import com.dumbdogdiner.stickychat.api.StickyChat;
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageResult;
import com.dumbdogdiner.stickychat.api.util.NotificationType;
import com.dumbdogdiner.stickychat.api.util.SoundUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an integration with StickyChat.
 */
public interface Integration {
    /**
     * Get the plugin this integration was created by.
     *
     * @return {@link Plugin}
     */
    @NotNull
    Plugin getPlugin();

    /**
     * Set the prefix for this integration. Returns the new prefix.
     *
     * @param prefix The prefix
     * @return {@link String}
     */
    @NotNull
    String setPrefix(@NotNull String prefix);

    /**
     * Return the current prefix for this integration.
     *
     * @return {@link  String}
     */
    @NotNull
    String getPrefix();

    /**
     * Return the chat API service.
     * @return {@link StickyChat}
     */
    default StickyChat getApi() {
        return StickyChat.getService();
    }

    /**
     * Send the target player a message from your plugin.
     *
     * @param player The target player
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendMessage(Player player, TextComponent message) {
        return this.getApi().getDirectMessageManager().sendRawMessage(player, Formatter.formatHexCodes(message));
    }

    /**
     * Send the target player a message from your plugin.
     *
     * @param player The target player
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendMessage(Player player, String message) {
        return this.getApi().getDirectMessageManager().sendRawMessage(player, Formatter.formatHexCodes(this.getPrefix() + message));
    }

    /**
     * Send the target console command sender a message from your plugin.
     *
     * @param sender The target sender
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendMessage(ConsoleCommandSender sender, TextComponent message) {
        sender.spigot().sendMessage(message);
        return DirectMessageResult.OK;
    }

    default DirectMessageResult sendMessage(ConsoleCommandSender sender, String message) {
        sender.spigot().sendMessage(Formatter.formatHexCodes(this.getPrefix() + message));
        return DirectMessageResult.OK;
    }

    /**
     * Send the target command sender a message.
     *
     * @param sender The target command sender
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            return this.sendMessage((Player) sender, message);
        }
        return this.sendMessage((ConsoleCommandSender) sender, message);
    }

    /**
     * Send the target sender a message with a notification sound.
     *
     * @param sender The target sender
     * @param notificationType The type of notification to send
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendMessageWithNotification(CommandSender sender, NotificationType notificationType, String message) {
        DirectMessageResult result = this.sendMessage(sender, message);
        // don't send the notification if it didn't work lol
        if (result == DirectMessageResult.OK) {
            SoundUtil.send(sender, notificationType);
        }
        return result;
    }

    /**
     * Send the target sender a message with an `INFO` notification sound.
     *
     * @param sender The target sender
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendWithInfo(CommandSender sender, String message) {
        return this.sendMessageWithNotification(sender, NotificationType.INFO, message);
    }

    /**
     * Send the target sender a message with an `QUIET` notification sound.
     *
     * @param sender The target sender
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendWithQuiet(CommandSender sender, String message) {
        return this.sendMessageWithNotification(sender, NotificationType.QUIET, message);
    }

    /**
     * Send the target sender a message with an `SUCCESS` notification sound.
     *
     * @param sender The target sender
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendWithSuccess(CommandSender sender, String message) {
        return this.sendMessageWithNotification(sender, NotificationType.SUCCESS, message);
    }

    /**
     * Send the target sender a message with an `ERROR` notification sound.
     *
     * @param sender The target sender
     * @param message The message to send
     * @return A {@link DirectMessageResult} determining the success of this action.
     */
    default DirectMessageResult sendWithError(CommandSender sender, String message) {
        return this.sendMessageWithNotification(sender, NotificationType.ERROR, "&c" + message);
    }
}
