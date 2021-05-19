package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import org.jetbrains.annotations.NotNull;

/**
 * Packet for acknowledging received direct messages.
 */
public interface DirectMessageAck extends Packet {
    @Override @NotNull
    default PacketType getType() {
        return PacketType.DIRECT_MESSAGE_ACK;
    }

    /**
     * Get the nonce of this packet.
     * @return A random {@link Integer}
     */
    Integer getNonce();
}
