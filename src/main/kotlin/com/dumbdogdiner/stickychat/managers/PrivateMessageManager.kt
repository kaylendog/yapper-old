package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.okkero.skedule.BukkitDispatcher
import kotlin.random.Random
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
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
     * Handle an incoming private message.
     */
    fun handleReceivedPrivateMessage(from: String, to: String, content: String) {
    }

    /**
     * Handle a received ACK packet.
     */
    fun handleReceivedAck(nonce: Int) {
        val pm = futurePrivateMessages[nonce] ?: return
        val job = messageAckTimeouts[nonce] ?: return

        job.cancel()

        // Todo: Send chat format
    }

    /**
     * Check and timeout a private message if no ACK has been received.
     */
    fun doAckTimeout(nonce: Int) {
        val pm = futurePrivateMessages[nonce] ?: return

        // De-reference
        futurePrivateMessages.remove(nonce)
        messageAckTimeouts.remove(nonce)

        // Todo: Send failed message.
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
}
