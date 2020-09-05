package com.dumbdogdiner.stickychatbukkit

import com.dumbdogdiner.stickychatcommon.Constants
import com.dumbdogdiner.stickychatcommon.MessageHandler
import com.dumbdogdiner.stickychatcommon.MessageType
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import java.io.DataInputStream
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

/**
 * Utility methods for plugin messaging.
 */
object PluginMessenger : Base, PluginMessageListener, MessageHandler {

    private const val CHANNEL_NAME = Constants.CHANNEL_NAME

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "BungeeCord") {
            return
        }

        handlePacket(ByteStreams.newDataInput(message))
    }

    /**
     * Message Event
     * - UTF - Player UUID
     * - UTF - Player name
     * - UTF - Message content (pre-colorized)
     */

    override fun handleMessage(data: DataInputStream) {
        if (!config.getBoolean("chat.incoming-cross-server-messaging", true)) {
            return
        }

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

    override fun handlePrivateMessage(data: DataInputStream) {
        val fromUuid = data.readUTF()
        val fromName = data.readUTF()
        val to = data.readUTF()

        val target = server.onlinePlayers.find { it.name == to } ?: return

        // Todo: Sent private message content

        val content = data.readUTF()
        val nonce = data.readInt()

        privateMessageManager.handleReceivedPrivateMessage(fromUuid, fromName, target, content)

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
     * - UTF - UUID
     * - Int - Nonce
     */
    override fun handlePrivateMessageAck(data: DataInputStream) {
        val uuid = data.readUTF()
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

    override fun handleMailReceive(data: DataInputStream) {
        val fromUuid = data.readUTF()
        val fromName = data.readUTF()
        val to = data.readUTF()

        val target = server.onlinePlayers.find { it.name == to } ?: return

        val content = data.readUTF()
        val createdAt = data.readLong()

        mailManager.handleReceivedMailMessage(fromUuid, fromName, target, content, createdAt)
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
    override fun sendPluginMessage(data: ByteArrayDataOutput) {
        sendTargetedPluginMessage(
            Bukkit.getOnlinePlayers().first(), data
        )
    }

    /**
     * Send a plugin message to the player with the given uuid.
     */
    override fun sendTargetedPluginMessage(uuid: String, data: ByteArrayDataOutput) {
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            return
        }
        sendTargetedPluginMessage(Bukkit.getOnlinePlayers().find { it.uniqueId.toString() == uuid }!!, data)
    }

    /**
     * Send a targeted plugin message via the specified player.
     */
    fun sendTargetedPluginMessage(target: Player, data: ByteArrayDataOutput) {
        val out = ByteStreams.newDataOutput()

        out.writeUTF("Forward")
        out.writeUTF("ALL")
        out.writeUTF(CHANNEL_NAME)

        out.writeShort(data.toByteArray().size)
        out.write(data.toByteArray())

        target.sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
    }
}
