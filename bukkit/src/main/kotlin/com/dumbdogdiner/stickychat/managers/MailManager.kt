package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.gui.LetterGui
import com.dumbdogdiner.stickychat.utils.FormatUtils.colorize
import com.dumbdogdiner.stickychat.utils.Priority
import com.dumbdogdiner.stickychat.utils.ServerUtils
import com.dumbdogdiner.stickychat.utils.SoundUtils
import java.text.SimpleDateFormat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

/**
 * Manages the sending, receiving, and persistent storage of mail messages.
 */
class MailManager : Base {
    /**
     * Check for new messages sent to the given player.
     */
    fun checkForMail(recipient: Player) {
        GlobalScope.launch {
            val letters = storageManager.fetchLettersForPlayer(recipient, true)
            if (letters.isEmpty()) {
                return@launch
            }
            chatManager.sendSystemMessage(recipient, "&bYou have &e${letters.size} &bnew letter${if (letters.size > 1) {"s"} else ""}!")
            SoundUtils.quietSuccess(recipient)
        }
    }

    /**
     * Create and send a mail message
     */
    fun sendMailMessage(from: Player, to: String, content: String) {
        val createdAt = System.currentTimeMillis()

        if (to.length < 3 || to.length > 16) {
            chatManager.sendSystemMessage(from, "&cInvalid player!")
            SoundUtils.error(from)
            return
        }

        val target = server.onlinePlayers.find { it.name == to }
        if (target != null) {
            sendLocalLetter(from, target, content, createdAt)
            return
        } else {
            sendRemoteLetter(from, to, content, createdAt)
        }

        ServerUtils.sendColorizedMessage(from, "&bYour message has been delivered!")
        SoundUtils.info(from)

        storageManager.savePartialLetter(from, to, content, createdAt)
    }

    /**
     * Fetch a player's mail.
     */
    fun readAllMail(to: Player, page: Int) {
        GlobalScope.launch {
            val letters = storageManager.fetchLettersForPlayer(to)
            LetterGui(to, letters)
        }
    }

    fun readUnreadMail(to: Player, page: Int) {
        GlobalScope.launch {
            storageManager.fetchLettersForPlayer(to, true)
        }
    }

    /**
     * Deliver a message to a player on the server.
     */
    private fun sendLocalLetter(from: Player, to: Player, content: String, createdAt: Long) {
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
    private fun sendRemoteLetter(from: Player, to: String, content: String, createdAt: Long) {
        messenger.sendMail(from, from.uniqueId.toString(), from.name, to, content, createdAt)
    }

    /**
     * Handle a received mail message from the plugin messenger.
     */
    fun handleReceivedMailMessage(fromUuid: String, fromName: String, to: Player, content: String, createdAt: Long) {
        if (config.getBoolean("mail.notify-on-arrival", true)) {
            chatManager.sendSystemMessage(to, createReceivedMailTextComponent(fromName))
            SoundUtils.quietSuccess(to)
        }

        storageManager.hydratePartialLetter(fromUuid, fromName, to, createdAt)
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
        hoverComponent.text = colorize("&bLetter from &e$fromName\n")
        hoverComponent.addExtra(colorize("&bUUID: &e$fromUuid\n"))
        hoverComponent.addExtra(colorize("&bSent: &e${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdAt)}\n"))
        hoverComponent.addExtra(colorize("&dClick to send a message."))

        message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(hoverComponent))
        message.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mail $fromName ")

        return message
    }
}
