package com.dumbdogdiner.stickychat.api.integration;

import com.dumbdogdiner.stickychat.api.StickyChat;
import com.dumbdogdiner.stickychat.api.result.DirectMessageResult;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents an integration with StickyChat.
 */
public interface Integration {
    public Plugin getPlugin();

    public String setPrefix(String prefix);
    public String getPrefix();

    /**
     * Send the target player a system message.
     *
     * @param player The player to send the message to
     * @param message The message to send
     * @return {@link DirectMessageResult}
     */
    default DirectMessageResult sendSystemMessage(Player player, BaseComponent message) {
        var component = new TextComponent();
        component.addExtra(this.getPrefix());
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
}
