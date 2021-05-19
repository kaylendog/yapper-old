package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Packet for cross-server messaging.
 */
interface SendMessage extends Packet {
    @Override @NotNull
    default PacketType getType() {
        return PacketType.SEND_MESSAGE;
    };

    /**
     * Get the UUID of the player who sent the message.
     * @return The player's {@link UUID}
     */
    UUID getSenderUniqueId();

    /**
     * Get the name of the player who sent the message.
     * @return A {@link String} containing the player's name.
     */
    String getSenderName();

    /**
     * Get the timestamp at which this message was sent.
     * @return {@link}
     */
    Long getTimestamp();

    /**
     * Get the raw content of this packet.
     * @return A {@link String} containing the raw content.
     */
    String getRawContent();
}
