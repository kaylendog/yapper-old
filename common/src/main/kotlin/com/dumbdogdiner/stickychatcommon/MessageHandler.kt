package com.dumbdogdiner.stickychatcommon

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams

interface MessageHandler {
    fun handlePacket(data: ByteArrayDataInput) {
        // read type of packet
        val type = MessageType.values()[data.readShort().toInt()]

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
    fun handleMessage(data: ByteArrayDataInput)

    /**
    * Private Message Event
    * - UTF - From player UUID
    * - UTF - To player name
    * - UTF - Message content
    * - Int - Nonce
    */

    fun handlePrivateMessage(data: ByteArrayDataInput)

    /**
    * Private Message ACK
    * - UTF - From
    * - Int - Nonce
    */

    fun handlePrivateMessageAck(data: ByteArrayDataInput)
    /**
    * Mail
    * - UTF - From uuid
    * - UTF - From name
    * - UTF - To name
    * - UTF - Content
    */

    fun handleMailReceive(data: ByteArrayDataInput)

    /**
    * Send a plugin message to Bungee.
    */
    fun sendPluginMessage(data: ByteArrayDataOutput)

    fun sendTargetedPluginMessage(uuid: String, data: ByteArrayDataOutput)

    /**
     * Utility method for quickly building output streams.
     */
    fun build(type: MessageType): ByteArrayDataOutput {
        val data = ByteStreams.newDataOutput()
        data.writeShort(type.ordinal.toInt())
        return data
    }
}
