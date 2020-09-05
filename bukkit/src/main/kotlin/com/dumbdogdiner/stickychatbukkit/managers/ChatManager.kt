package com.dumbdogdiner.stickychatbukkit.managers

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.PluginMessenger
import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils
import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils.colorize
import com.dumbdogdiner.stickychatbukkit.utils.Priority
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

    init {
        logger.info("Cross-server messaging has been ${ if (config.getBoolean("chat.cross-server-messaging", true)) {"enabled"} else {"disabled"}}")
    }

    /**
     * Broadcast a given message to all players.
     */
    fun sendMessageToAllPlayers(priority: Priority = Priority.ALL, baseComponent: BaseComponent) {
        for (it in server.onlinePlayers) {
            sendMessage(it, priority, baseComponent)
        }
        server.logger.info(baseComponent.toPlainText())
    }

    /**
     * Broadcast a global chat message to this server.
     */
    fun sendGlobalChatMessage(fromUuid: String, fromName: String, content: String) {
        val message = TextComponent()
        message.text = content
        message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(createHoverComponent(fromName, fromUuid)))
        message.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg $fromName ")

        sendMessageToAllPlayers(Priority.ALL, message)
    }

    /**
     * Broadcast a player message to all players on the server, and forward it through bungee to
     * other servers on the network.
     */
    fun broadcastPlayerMessage(player: Player, content: String) {
        var formattedContent = FormatUtils.formatGlobalChatMessage(player, content)
        sendGlobalChatMessage(player.uniqueId.toString(), player.name, formattedContent)

        if (config.getBoolean("chat.cross-server-messaging", true)) {
            formattedContent = FormatUtils.formatOutgoingGlobalChatMessage(player, content)
            PluginMessenger.broadcastMessage(player, formattedContent)
        }
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
     * Send a message to a given player using a string of content.
     */
    fun sendMessage(player: Player, priority: Priority = Priority.ALL, content: String) {
        val component = TextComponent()
        component.text = content
        sendMessage(player, priority, component)
    }

    /**
     * Send a system message to the specified player, formatting the content with the plugin prefix.
     */
    fun sendSystemMessage(player: Player, content: String) {
        val component = TextComponent()
        component.text = colorize("${config.getString("prefix")}$content")
        sendSystemMessage(player, component)
    }

    /**
     * Send a component to the specified player, marked as SYSTEM.
     */
    fun sendSystemMessage(player: Player, component: BaseComponent) {
        sendMessage(player, Priority.SYSTEM, component)
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
            playerChatPriority[player] = Priority.ALL
            forcePlayerChatPriorityUpdate(player)
            Priority.ALL
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
        component.text = colorize("&bMessage from &e$name\n")
        component.addExtra(colorize("&bUUID: &e$uuid\n"))
        component.addExtra(colorize("&bSent: &e${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(Instant.now()))}\n"))
        component.addExtra(colorize("&dClick to send a message."))

        return component
    }
}
