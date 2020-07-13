package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.Priority
import com.dumbdogdiner.stickychat.utils.StringUtils
import java.text.SimpleDateFormat
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

/**
 * Manages the sending, receiving, and persistent storage of mail messages.
 */
class MailManager : Base {
    /**
     * Create and send a mail message
     */
    fun sendMailMessage(from: Player, to: String, content: String) {
        val createdAt = System.currentTimeMillis()

        val target = server.onlinePlayers.find { it.name == to }
        if (target != null) {
            sendLocalMailMessage(from, target, content, createdAt)
            return
        }

        sendRemoteMailMessage(from, to, content, createdAt)
        saveMailMessage(from, to, content, createdAt)
    }

    /**
     * Deliver a message to a player on the server.
     */
    fun sendLocalMailMessage(from: Player, to: Player, content: String, createdAt: Long) {
        chatManager.sendMessage(to, Priority.DIRECT, createMailTextComponent(from.uniqueId.toString(), from.name, to.name, content, createdAt))
    }

    /**
     * Attempt to deliver a mail message to someone on the network.
     */
    fun sendRemoteMailMessage(from: Player, to: String, content: String, createdAt: Long) {
        messenger.sendMail(from, from.uniqueId.toString(), from.name, to, content, createdAt)
    }

    /**
     * Save a message to storage.
     */
    fun saveMailMessage(player: Player, to: String, content: String, createdAt: Long) {
        storageManager.saveMailMessage(player, to, content, createdAt)
    }

    /**
     * Handle a received mail message from the plugin messenger.
     */
    fun handleReceivedMailMessage(fromUuid: String, fromName: String, to: String, content: String, createdAt: Long) {
        if (!config.getBoolean("mail.notify-on-arrival", true)) {
            return
        }

        val target = server.onlinePlayers.find { it.uniqueId.toString() == fromUuid } ?: return
        chatManager.sendMessage(target, Priority.DIRECT, createMailTextComponent(fromUuid, fromName, to, content, createdAt))
    }

    /**
     * Create the text component used for viewing a received mail message.
     */
    private fun createMailTextComponent(fromUuid: String, fromName: String, to: String, content: String, createdAt: Long): TextComponent {
        val message = TextComponent()
        message.text = content

        val hoverComponent = TextComponent()
        hoverComponent.text = StringUtils.colorize("&bMessage from &e$fromName\n")
        hoverComponent.addExtra(StringUtils.colorize("&bUUID: &e$fromUuid\n"))
        hoverComponent.addExtra(StringUtils.colorize("&bSent: &e${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdAt)}\n"))
        hoverComponent.addExtra(StringUtils.colorize("&aClick to send a message."))

        message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(hoverComponent))
        message.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mail $fromName ")

        return message
    }
}
