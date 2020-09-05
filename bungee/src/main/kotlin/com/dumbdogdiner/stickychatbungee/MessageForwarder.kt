package com.dumbdogdiner.stickychatbungee

import com.dumbdogdiner.stickychatcommon.Constants
import com.dumbdogdiner.stickychatcommon.MessageHandler
import com.dumbdogdiner.stickychatcommon.MessageType
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

/**
 * Forwards messages from a single node to other server nodes.
 */
object MessageForwarder : Base, Listener, MessageHandler {

    private const val CHANNEL_NAME = Constants.CHANNEL_NAME

    /**
     * Handle a plugin message received from a server instance.
     */
    @EventHandler
    fun onPluginMessageReceived(ev: PluginMessageEvent) {
        if (ev.tag != CHANNEL_NAME) {
            return
        }

        logger.info("Received message from node - decoding")

        // read type of packet
        val input = ByteStreams.newDataInput(ev.data)
        System.out.println(ev.data.size)
        val type: MessageType = MessageType.values()[input.readShort().toInt()]

        when (type) {
            MessageType.MESSAGE -> handleMessage(
                input
            )
            MessageType.PRIVATE_MESSAGE -> handlePrivateMessage(input)
            MessageType.PRIVATE_MESSAGE_ACK -> handlePrivateMessageAck(input)
            MessageType.MAIL -> handleMailReceive(input)
        }
    }

    override fun handleMessage(data: ByteArrayDataInput) {
        logger.info("Got message packet - broadcasting to nodes")

        // this is dumb
        val uuid = data.readUTF()
        val name = data.readUTF()
        val content = data.readUTF()

        val msg = build(MessageType.MESSAGE)
        msg.writeUTF(uuid)
        msg.writeUTF(name)
        msg.writeUTF(content)

        sendPluginMessage(msg)
    }

    override fun handlePrivateMessage(data: ByteArrayDataInput) {
        logger.info("Got private message packet - attempting to forward to targeted player")

        val uuid = data.readUTF()
        val name = data.readUTF()
        val content = data.readUTF()
        val nonce = data.readInt()

        val msg = build(MessageType.PRIVATE_MESSAGE)
        msg.writeUTF(uuid)
        msg.writeUTF(name)
        msg.writeUTF(content)
        msg.writeShort(nonce)

        sendTargetedPluginMessage(uuid, msg)
    }

    override fun handlePrivateMessageAck(data: ByteArrayDataInput) {
        logger.info("Got private messace ACK packet - attempting to forward to targeted player")

        val uuid = data.readUTF()
        val nonce = data.readInt()

        val msg = build(MessageType.PRIVATE_MESSAGE_ACK)
        msg.writeUTF(uuid)
        msg.writeInt(nonce)

        sendTargetedPluginMessage(uuid, msg)
    }

    override fun handleMailReceive(data: ByteArrayDataInput) {
        logger.info("Got mail received packet - broadcasting to nodes")

        val uuid = data.readUTF()
        val name = data.readUTF()
        val to = data.readUTF()
        val content = data.readUTF()

        val msg = build(MessageType.MAIL)
        msg.writeUTF(uuid)
        msg.writeUTF(name)
        msg.writeUTF(to)
        msg.writeUTF(content)

        sendPluginMessage(msg)
    }

    /**
     * Sends a global plugin message to all servers - not guaranteed to be received on
     * all servers.
     */
    override fun sendPluginMessage(data: ByteArrayDataOutput) {
        proxy.servers.values.forEach {
            it.players.firstOrNull()?.sendData(CHANNEL_NAME, data.toByteArray())
        }
    }

    /**
     * Send a plugin message to the player with the specified id - not guaranteed to be
     * received.
     */
    override fun sendTargetedPluginMessage(uuid: String, data: ByteArrayDataOutput) {
        for (server in proxy.servers.values) {
            for (player in server.players) {
                if (player.uniqueId.toString() == uuid) {
                    player.sendData(CHANNEL_NAME, data.toByteArray())
                    return
                }
            }
        }
        logger.warning("Failed to find proxied player with uuid '$uuid'")
    }
}
