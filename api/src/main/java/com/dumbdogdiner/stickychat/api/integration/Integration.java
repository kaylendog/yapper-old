package com.dumbdogdiner.stickychat.api.integration;

import com.dumbdogdiner.stickychat.api.Formatter;
import com.dumbdogdiner.stickychat.api.StickyChat;
import com.dumbdogdiner.stickychat.api.result.DirectMessageResult;
import com.dumbdogdiner.stickychat.api.util.NotificationType;
import com.dumbdogdiner.stickychat.api.util.SoundUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
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
    public Plugin getPlugin();

    /**
     * Set the prefix for this integration. Returns the new prefix.
     *
     * @param prefix The prefix
     * @return {@link String}
     */
    @NotNull
    public String setPrefix(@NotNull String prefix);

    /**
     * Return the current prefix for this integration.
     *
     * @return {@link  String}
     */
    @NotNull
    public String getPrefix();

    /**
     * Send the target a system message.
     *
     * @param sender The target to send the message to - can be the ConsoleSender
     * @param message The message to send
     * @return {@link DirectMessageResult}
     */
    default DirectMessageResult sendSystemMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            return sendSystemMessage((Player) sender, message);
        }
        sender.sendMessage(message);
        return DirectMessageResult.OK;
    }

    /**
     * Send the target player a system message.
     *
     * @param player The player to send the message to
     * @param message The message to send
     * @return {@link DirectMessageResult}
     */
    default DirectMessageResult sendSystemMessage(Player player, BaseComponent message) {
        var component = new TextComponent();
        component.addExtra(Formatter.colorize(this.getPrefix()));
        component.addExtra(message);
        return StickyChat.getService().getDirectMessageService(player).sendSystemMessage(component);
    }

    /**
     * Send the target player a system message.
     *
     * @param player The player to send the message to
     * @param message The message to send
     * @return {@link DirectMessageResult}
     */
    default DirectMessageResult sendSystemMessage(Player player, String message) {
        return this.sendSystemMessage(player, new TextComponent(message));
    }

    /**
     * Send the target a system error.
     *
     * @param sender The target to send the error to
     * @param error The error to send
     * @return {@link DirectMessageResult}
     */
    default DirectMessageResult sendSystemError(CommandSender sender, String error) {
        SoundUtil.send(sender, NotificationType.ERROR);
        return this.sendSystemMessage(sender, Formatter.colorize("&c" + error));
    }

}
