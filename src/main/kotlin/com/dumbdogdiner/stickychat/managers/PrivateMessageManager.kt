package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.Priority
import com.dumbdogdiner.stickychat.utils.ServerUtils
import com.dumbdogdiner.stickychat.utils.SoundUtils
import com.dumbdogdiner.stickychat.utils.StringUtils
import com.okkero.skedule.BukkitDispatcher
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import kotlin.collections.HashMap
import kotlin.random.Random
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

/**
 * Manages the receiving and sending of private messages.
 */
class PrivateMessageManager : Base {
    private val nonceGenerator = Random(System.currentTimeMillis().toInt())

    private val messageAckTimeouts = HashMap<Int, Job>()
    private val futurePrivateMessages = HashMap<Int, PrivateMessage>()

    /**
     * Send a private message to a player.
     */
    fun sendPrivateMessage(from: Player, to: String, content: String) {
        val target = server.onlinePlayers.find { it.name == to }
        if (target != null) {
            sendLocalPrivateMessage(from, target, content)
            return
        }
        sendRemotePrivateMessage(from, to, content)
    }

    /**
     * Send a local private message between players on the same server.
     */
    fun sendLocalPrivateMessage(from: Player, to: Player, content: String) {
        if (from == to) {
            ServerUtils.sendColorizedMessage(from, "&cYou cannot send a message to yourself!")
            SoundUtils.error(from)
            return
        }

        logger.info("[PM] ${from.name} => ${to.name}: $content")
        chatManager.sendMessage(
                from,
                Priority.DIRECT,
                createPrivateMessageTextComponent(from.uniqueId.toString(), from.name, content)
        )
        SoundUtils.info(from)
        chatManager.sendMessage(
            to,
            Priority.DIRECT,
            createPrivateMessageTextComponent(from.uniqueId.toString(), from.name, content)
        )
        SoundUtils.info(to)
    }

    fun sendRemotePrivateMessage(from: Player, to: String, content: String) {
        val nonce = nonceGenerator.nextInt()
        futurePrivateMessages[nonce] = PrivateMessage(from, to, content, nonce)

        messenger.broadcastPrivateMessage(from, to, content, nonce)

        // Timeout after 1000ms
        messageAckTimeouts[nonce] = GlobalScope.launch(BukkitDispatcher(plugin)) {
            delay(1000)
            if (!this.isActive) return@launch
            doAckTimeout(nonce)
        }
    }

    /**
     * Handle an incoming private message from the plugin messenger.
     */
    fun handleReceivedPrivateMessage(fromUuid: String, fromName: String, to: String, content: String) {
        val target = server.onlinePlayers.find { it.name == to } ?: return

        chatManager.sendMessage(
            target,
            Priority.DIRECT,
            createPrivateMessageTextComponent(fromUuid, fromName, content)
        )
        SoundUtils.info(target)

        logger.info("[PM] $fromName => ${target.name}: $content")
    }

    /**
     * Handle a received ACK packet.
     */
    fun handleReceivedAck(nonce: Int) {
        val pm = futurePrivateMessages[nonce] ?: return
        val job = messageAckTimeouts[nonce] ?: return

        job.cancel()
        // Todo: Send chat format
        chatManager.sendMessage(
            pm.from,
            Priority.DIRECT,
            createPrivateMessageTextComponent(pm.from.uniqueId.toString(), pm.from.name, pm.content)
        )
        logger.info("Private message for ${pm.from} to user ${pm.to} timed out.")
    }

    /**
     * Check and timeout a private message if no ACK has been received.
     */
    fun doAckTimeout(nonce: Int) {
        val pm = futurePrivateMessages[nonce] ?: return

        // De-reference
        futurePrivateMessages.remove(nonce)
        messageAckTimeouts.remove(nonce)

        chatManager.sendMessage(pm.from, Priority.SYSTEM, "&cFailed to deliver direct message to player &b${pm.to} &c- perhaps they aren't online?")
    }

    /**
     * Data class for storing future private messages.
     */
    private class PrivateMessage(
        val from: Player,
        val to: String,
        val content: String,
        val nonce: Int
    )

    /**
     * Create the text component used for viewing a received mail message.
     */
    private fun createPrivateMessageTextComponent(fromUuid: String, fromName: String, content: String): TextComponent {
        val message = TextComponent()
        message.text = content

        val hoverComponent = TextComponent()
        hoverComponent.text = StringUtils.colorize("&bMessage from &e$fromName\n")
        hoverComponent.addExtra(StringUtils.colorize("&bUUID: &e$fromUuid\n"))
        hoverComponent.addExtra(StringUtils.colorize("&bSent: &e${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(Instant.now()))}\n"))
        hoverComponent.addExtra(StringUtils.colorize("&aClick to reply to this message."))

        message.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(hoverComponent))
        message.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg $fromName ")

        return message
    }
}
