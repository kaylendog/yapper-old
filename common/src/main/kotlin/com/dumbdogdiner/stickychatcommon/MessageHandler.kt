package com.dumbdogdiner.stickychatcommon

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import java.io.ByteArrayInputStream
import java.io.DataInputStream

interface MessageHandler {
    fun handlePacket(input: ByteArrayDataInput) {
        // read type of packet
        val type = MessageType.values()[input.readShort().toInt()]

        val len = input.readShort()
        val msgbytes = ByteArray(len.toInt())
        input.readFully(msgbytes)
        val data = DataInputStream(ByteArrayInputStream(msgbytes))

        when (type) {
            MessageType.MESSAGE -> handleMessage(data)
            MessageType.PRIVATE_MESSAGE -> handlePrivateMessage(data)
            MessageType.PRIVATE_MESSAGE_ACK -> handlePrivateMessageAck(data)
            MessageType.MAIL -> handleMailReceive(data)
        }
    }

    /**
     * Message Event
     * - UTF - Player UUID
     * - UTF - Player name
     * - UTF - Message content (pre-colorized)
     */
    fun handleMessage(data: DataInputStream)

    /**
    * Private Message Event
    * - UTF - From player UUID
    * - UTF - To player name
    * - UTF - Message content
    * - Int - Nonce
    */

    fun handlePrivateMessage(data: DataInputStream)

    /**
    * Private Message ACK
    * - UTF - From
    * - Int - Nonce
    */

    fun handlePrivateMessageAck(data: DataInputStream)
    /**
    * Mail
    * - UTF - From uuid
    * - UTF - From name
    * - UTF - To name
    * - UTF - Content
    */

    fun handleMailReceive(data: DataInputStream)

    /**
    * Send a plugin message to Bungee.
    */
    fun sendPluginMessage(data: ByteArrayDataOutput)

    fun sendTargetedPluginMessage(uuid: String, data: ByteArrayDataOutput)
}
