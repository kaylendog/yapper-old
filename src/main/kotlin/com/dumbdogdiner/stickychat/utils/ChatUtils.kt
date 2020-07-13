package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.PluginMessenger
import com.dumbdogdiner.stickychat.utils.StringUtils.colorize
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

/**
 * Utility methods for sending and broadcasting messages.
 */
object ChatUtils : Base {
    fun broadcastPlayerMessage(player: Player, message: String) {
        val withFormatting = colorize(config.getString("chat.format", "&8%name%: %message%")!!)
            .replace("%name%", if (player.hasPermission("stickychat.colorizeNick")) colorize(player.displayName) else player.displayName)
            .replace("%message%", if (player.hasPermission("stickychat.colorizeMessage")) colorize(message) else message)

        // This needs to be better
        val format = PlaceholderUtils.setPlaceholdersSafe(
            player,
            withFormatting
        ).replace("%", "")

        broadcastPlayerMessage(player.name, player.uniqueId.toString(), format)
        PluginMessenger.broadcastMessage(player, format)
    }

    /**
     * Broadcast a text-based message to all players on the server.
     */
    fun broadcastPlayerMessage(name: String, uuid: String, content: String) {
        val message = TextComponent()
        message.text = content
        message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, listOf(createHoverComponent(name, uuid)).toTypedArray())
        message.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg $name ")

        server.onlinePlayers.forEach {
            it.spigot().sendMessage(message)
        }
        server.logger.info(content)
    }

    /**
     * Create the hover component, used for displaying message info when a user hovers over a message.
     */
    private fun createHoverComponent(name: String, uuid: String): TextComponent {
        val component = TextComponent()
        // Todo: Check if extras can be looped over for colorization?
        component.text = colorize("&bMessage from &e$name\n")
        component.addExtra(colorize("&bUUID: &e$uuid\n"))
        component.addExtra(colorize("&bSent: &e${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(Instant.now()))}\n"))
        component.addExtra(colorize("&aClick to send a message."))

        return component
    }
}
