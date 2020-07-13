package com.dumbdogdiner.stickychat

import com.dumbdogdiner.stickychat.utils.ChatUtils
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

/**
 * Utility methods for plugin messaging.
 */
object PluginMessenger : Base, PluginMessageListener {

    private const val CHANNEL_NAME = "StickyChat"

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "BungeeCord") {
            return
        }

        val input = ByteStreams.newDataInput(message)
        val subchannel = input.readUTF()

        if (subchannel != CHANNEL_NAME) {
            return
        }

        val len = input.readShort()
        val msgbytes = ByteArray(len.toInt())
        input.readFully(msgbytes)

        val msgin = DataInputStream(ByteArrayInputStream(msgbytes))
        val type: MessageType = MessageType.values()[msgin.readShort().toInt()]

        when (type) {
            MessageType.MESSAGE -> handleMessage(msgin)
            MessageType.PRIVATE_MESSAGE -> handlePrivateMessage(msgin)
            MessageType.PRIVATE_MESSAGE_ACK -> handlePrivateMessageAck(msgin)
        }
    }

    /**
     * Message Event
     * - UTF - Player name
     * - UTF - Player UUID
     * - UTF - Message content (pre-colorized)
     */

    private fun handleMessage(data: DataInputStream) {
        ChatUtils.broadcastPlayerMessage(data.readUTF(), data.readUTF(), data.readUTF())
    }

    fun broadcastMessage(player: Player, message: String) {
        val out = ByteStreams.newDataOutput()
        out.writeShort(MessageType.MESSAGE.ordinal)

        out.writeUTF(player.name)
        out.writeUTF(player.uniqueId.toString())
        out.writeUTF(message)

        sendTargetedPluginMessage(player, out)
    }

    /**
     * Private Message Event
     * - UTF - From player UUID
     * - UTF - To player name
     * - UTF - Message content
     * - Int - Nonce
     */

    private fun handlePrivateMessage(data: DataInputStream) {
        val from = data.readUTF()
        val to = data.readUTF()

        val target = server.onlinePlayers.find { it.name == to } ?: return

        // Todo: Sent private message content

        val content = data.readUTF()
        val nonce = data.readInt()

        privateMessageManager.handleReceivedPrivateMessage(from, to, content)

        broadcastPrivateMessageAck(target, nonce)
    }

     fun broadcastPrivateMessage(player: Player, target: String, message: String, nonce: Int) {
        val out = ByteStreams.newDataOutput()
        out.writeShort(MessageType.PRIVATE_MESSAGE.ordinal)

        out.writeUTF(player.uniqueId.toString())
        out.writeUTF(target)
        out.writeUTF(message)
        out.writeInt(nonce)

        sendTargetedPluginMessage(player, out)
    }

    /**
     * Private Message ACK
     * - Int - Nonce
     */

    private fun handlePrivateMessageAck(data: DataInputStream) {
        val nonce = data.readInt()
        privateMessageManager.handleReceivedAck(nonce)
    }

    private fun broadcastPrivateMessageAck(player: Player, nonce: Int) {
        val out = ByteStreams.newDataOutput()
        out.writeShort(MessageType.PRIVATE_MESSAGE_ACK.ordinal)
        out.writeInt(nonce)
        sendTargetedPluginMessage(player, out)
    }

    /**
     * Mail Receive
     * - Int - ID
     */

    private fun handleMailReceive(data: DataInputStream) {}

    fun broadcastMailReceive(player: Player) { }

    /**
     * Send a plugin message to Bungee.
     */
    private fun sendPluginMessage(data: ByteArrayDataOutput) {
        sendTargetedPluginMessage(Bukkit.getOnlinePlayers().first(), data)
    }

    /**
     * Send a targeted plugin message via the specified player.
     */
    private fun sendTargetedPluginMessage(target: Player, data: ByteArrayDataOutput) {
        val out = ByteStreams.newDataOutput()

        out.writeUTF("Forward")
        out.writeUTF("ALL")
        out.writeUTF(CHANNEL_NAME)

        out.writeShort(data.toByteArray().size)
        out.write(data.toByteArray())

        target.sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
    }

    /**
     * Enum of possible bungee message types.
     */
    private enum class MessageType {
        MESSAGE,
        PRIVATE_MESSAGE,
        PRIVATE_MESSAGE_ACK,
        MAIL_RECEIVE
    }
}
