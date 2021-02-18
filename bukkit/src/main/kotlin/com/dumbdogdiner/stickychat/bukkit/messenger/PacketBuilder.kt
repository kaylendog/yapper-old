package com.dumbdogdiner.stickychat.bukkit.messenger

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams
import java.util.Base64
import java.util.UUID

/**
 * Builds packets for redis pub-sub.
 */
class PacketBuilder(private val type: Type) {

    /**
     * Represents a decoded packet received from Redis.
     */
    class DecodedPacked(val uniqueId: UUID, val type: Type, val sender: String, val recipient: String?, val content: String)

    companion object {
        /**
         * Decode a base64 encoded packet from Redis.
         */
        fun decodeBase64(incoming: String): DecodedPacked {
            return decodePacket(Base64.getDecoder().decode(incoming))
        }

        /**
         * Decode a packet from Redis.
         */
        fun decodePacket(incoming: ByteArray): DecodedPacked {
            return decodePacket(ByteStreams.newDataInput(incoming))
        }

        /**
         * Decode a packet from redis pub/sub.
         */
        fun decodePacket(incoming: ByteArrayDataInput): DecodedPacked {
            val type = Type.values()[incoming.readInt()]
            val uuid = UUID(incoming.readLong(), incoming.readLong())

            val sender = incoming.readUTF()
            var recipient: String? = null
            if (type == Type.DM_MESSAGE) { recipient = incoming.readUTF() }

            val content = incoming.readUTF()

            return DecodedPacked(uuid, type, sender, recipient, content)
        }
    }

    var uniqueId = UUID.randomUUID()
    private val out = ByteStreams.newDataOutput()

    // data
    private lateinit var sender: String
    private var recipient: String? = null
    private lateinit var content: String

    enum class Type {
        MESSAGE,
        DM_MESSAGE,
        STAFF_CHAT
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
        // write id
        out.writeLong(this.uniqueId.mostSignificantBits)
        out.writeLong(this.uniqueId.leastSignificantBits)

        out.writeInt(this.type.ordinal)

        out.writeUTF(this.sender)
        if (this.recipient != null) {
            out.writeUTF(this.recipient as String)
        }
        out.writeUTF(this.content)
        return out.toByteArray()
    }
}
