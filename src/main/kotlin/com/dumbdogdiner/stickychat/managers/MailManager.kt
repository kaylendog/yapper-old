package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.FormatUtils
import com.dumbdogdiner.stickychat.utils.FormatUtils.colorize
import com.dumbdogdiner.stickychat.utils.Priority
import com.dumbdogdiner.stickychat.utils.ServerUtils
import com.dumbdogdiner.stickychat.utils.SoundUtils
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
        } else {
            sendRemoteMailMessage(from, to, content, createdAt)
        }

        ServerUtils.sendColorizedMessage(from, "&bYour message has been delivered!")
        SoundUtils.info(from)

        saveMailMessage(from, to, content, createdAt)
    }

    /**
     * Deliver a message to a player on the server.
     */
    fun sendLocalMailMessage(from: Player, to: Player, content: String, createdAt: Long) {
        if (from == to) {
            ServerUtils.sendColorizedMessage(from, "&cYou cannot send a letter to yourself!")
            SoundUtils.error(from)
            return
        }

        chatManager.sendMessage(to, Priority.DIRECT, createMailTextComponent(from.uniqueId.toString(), from.name, to.name, content, createdAt))
        SoundUtils.info(to)
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
    fun handleReceivedMailMessage(fromUuid: String, fromName: String, to: Player, content: String, createdAt: Long) {
        if (!config.getBoolean("mail.notify-on-arrival", true)) {
            return
        }
        chatManager.sendSystemMessage(to, createReceivedMailTextComponent(fromName))
        SoundUtils.quietSuccess(to)
    }

    /**
     * Create the text component used for notifying a player of a new letter.
     */
    private fun createReceivedMailTextComponent(fromName: String): TextComponent {
        val component = TextComponent()
        component.text = colorize("&bYou have received a new letter from &e$fromName&b!}")

        val clickComponent = TextComponent()
        clickComponent.text = colorize("&f&l[&aOPEN&l]")
        clickComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mail open")

        component.addExtra(clickComponent)

        return component
    }

    /**
     * Create the text component used for viewing a received mail message.
     */
    private fun createMailTextComponent(fromUuid: String, fromName: String, to: String, content: String, createdAt: Long): TextComponent {
        val message = TextComponent()
        message.text = content

        val hoverComponent = TextComponent()
        hoverComponent.text = FormatUtils.colorize("&bLetter from &e$fromName\n")
        hoverComponent.addExtra(FormatUtils.colorize("&bUUID: &e$fromUuid\n"))
        hoverComponent.addExtra(FormatUtils.colorize("&bSent: &e${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdAt)}\n"))
        hoverComponent.addExtra(FormatUtils.colorize("&bClick to send a message."))

        message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(hoverComponent))
        message.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mail $fromName ")

        return message
    }
}
