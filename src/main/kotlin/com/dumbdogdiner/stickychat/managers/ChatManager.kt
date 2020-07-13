package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.PluginMessenger
import com.dumbdogdiner.stickychat.utils.ChatUtils
import com.dumbdogdiner.stickychat.utils.PlaceholderUtils
import com.dumbdogdiner.stickychat.utils.Priority
import com.dumbdogdiner.stickychat.utils.StringUtils
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import kotlin.collections.HashMap
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

/**
 * Manages chat for individual players
 */
class ChatManager : Base {
    private val playerChatPriority = HashMap<Player, Priority>()

    /**
     * Broadcast a message to all players.
     */
    fun sendMessageToAllPlayers(priority: Priority = Priority.ALL, baseComponent: BaseComponent) {
        for (it in server.onlinePlayers) {
            sendMessage(it, priority, baseComponent)
        }
        logger.info("[GLOBAL] ${baseComponent.toPlainText()}")
    }

    fun broadcastPlayerMessage(player: Player, message: String) {
        val withFormatting = StringUtils.colorize(ChatUtils.config.getString("chat.format", "&8%name%: %message%")!!)
                .replace("%name%", if (player.hasPermission("stickychat.colorizeNick")) StringUtils.colorize(player.displayName) else player.displayName)
                .replace("%message%", if (player.hasPermission("stickychat.colorizeMessage")) StringUtils.colorize(message) else message)

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
        message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(createHoverComponent(name, uuid)))
        message.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg $name ")
        sendMessageToAllPlayers(Priority.ALL, message)
    }

    /**
     * Send a message to a given player.
     */
    fun sendMessage(player: Player, priority: Priority = Priority.ALL, baseComponent: BaseComponent) {
        if (priority.ordinal >= getChatPriority(player).ordinal) {
            player.spigot().sendMessage(baseComponent)
        }
    }

    /**
     * Set a player's chat priority.
     */
    fun setChatPriority(player: Player, priority: Priority) {
        playerChatPriority[player] = priority
    }

    /**
     * Fetches a player's chat priority from the StorageManager - if the player
     * has no cached priority, it assumes system messages only.
     */
    fun getChatPriority(player: Player): Priority {
        return playerChatPriority[player] ?: {
            // In case data hasn't been retrieved yet.
            playerChatPriority[player] = Priority.IMPORTANT
            forcePlayerChatPriorityUpdate(player)
            Priority.IMPORTANT
        }()
    }

    /**
     * Force a database update for the given player's chat priority.
     */
    private fun forcePlayerChatPriorityUpdate(player: Player) {
    }

    /**
     * Create the hover component, used for displaying message info when a user hovers over a message.
     */
    private fun createHoverComponent(name: String, uuid: String): TextComponent {
        val component = TextComponent()
        // Todo: Check if extras can be looped over for colorization?
        component.text = StringUtils.colorize("&bMessage from &e$name\n")
        component.addExtra(StringUtils.colorize("&bUUID: &e$uuid\n"))
        component.addExtra(StringUtils.colorize("&bSent: &e${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(Instant.now()))}\n"))
        component.addExtra(StringUtils.colorize("&aClick to send a message."))

        return component
    }
}
