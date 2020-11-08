package com.dumbdogdiner.stickychat.bukkit.redis

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams

/**
 * Builds packets for redis pub-sub.
 */
class PacketBuilder(private val type: Type) {
    class DecodedPacked(val type: Type, val sender: String, val recipient: String?, content: String)

    companion object {
        /**
         * Decode a packet from redis pubsub.
         */
        fun decodePacket(incoming: ByteArrayDataInput): DecodedPacked {
            val type = Type.values()[incoming.readInt()]
            val sender = incoming.readUTF()
            var recipient: String? = null
            if (type == Type.DM_MESSAGE) { recipient = incoming.readUTF() }
            val content = incoming.readUTF()
            return DecodedPacked(type, sender, recipient, content)
        }
    }

    private val out = ByteStreams.newDataOutput()

    // data
    private lateinit var sender: String
    private var recipient: String? = null
    private lateinit var content: String

    enum class Type {
        MESSAGE,
        DM_MESSAGE
    }

    /**
     * Set the sender of this packet.
     */
    fun sender(name: String): PacketBuilder {
        this.sender = name
        return this
    }

    /**
     * Set the recipient of this packet.
     */
    fun recipient(name: String): PacketBuilder {
        if (this.type != Type.DM_MESSAGE) {
            throw RuntimeException("Cannot set the recipient of a non-DM type packet")
        }
        this.recipient = name
        return this
    }

    /**
     * Set the content of this packet.
     */
    fun content(content: String): PacketBuilder {
        this.content = content
        return this
    }

    /**
     * Build this packet.
     */
    fun build(): ByteArray {
        out.writeInt(this.type.ordinal)
        out.writeUTF(this.sender)
        if (this.recipient != null) {
            out.writeUTF(this.recipient as String)
        }
        out.writeUTF(this.content)
        return out.toByteArray()
    }
}
