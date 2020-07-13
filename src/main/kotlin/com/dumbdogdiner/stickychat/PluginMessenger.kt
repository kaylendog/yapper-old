package com.dumbdogdiner.stickychat

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
            MessageType.MAIL -> handleMailReceive(msgin)
        }
    }

    /**
     * Message Event
     * - UTF - Player UUID
     * - UTF - Player name
     * - UTF - Message content (pre-colorized)
     */

    private fun handleMessage(data: DataInputStream) {
        chatManager.sendGlobalChatMessage(data.readUTF(), data.readUTF(), data.readUTF())
    }

    fun broadcastMessage(player: Player, message: String) {
        val out = ByteStreams.newDataOutput()
        out.writeShort(MessageType.MESSAGE.ordinal)

        out.writeUTF(player.uniqueId.toString())
        out.writeUTF(player.name)
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
        val fromUuid = data.readUTF()
        val fromName = data.readUTF()
        val to = data.readUTF()

        val target = server.onlinePlayers.find { it.name == to } ?: return

        // Todo: Sent private message content

        val content = data.readUTF()
        val nonce = data.readInt()

        privateMessageManager.handleReceivedPrivateMessage(fromUuid, fromName, to, content)

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
     * Mail
     * - UTF - From uuid
     * - UTF - From name
     * - UTF - To name
     * - UTF - Content
     */

    private fun handleMailReceive(data: DataInputStream) {
        val fromUuid = data.readUTF()
        val fromName = data.readUTF()
        val to = data.readUTF()
        val content = data.readUTF()
        val createdAt = data.readLong()

        mailManager.handleReceivedMailMessage(fromUuid, fromName, to, content, createdAt)
    }

    fun sendMail(target: Player, fromUuid: String, fromName: String, toName: String, content: String, createdAt: Long) {
        val out = ByteStreams.newDataOutput()
        out.writeShort(MessageType.MAIL.ordinal)

        out.writeUTF(fromUuid)
        out.writeUTF(fromName)
        out.writeUTF(toName)
        out.writeUTF(content)
        out.writeLong(createdAt)

        sendTargetedPluginMessage(target, out)
    }

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
        MAIL
    }
}
