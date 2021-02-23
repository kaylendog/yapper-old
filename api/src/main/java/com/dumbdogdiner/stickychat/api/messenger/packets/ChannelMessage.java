package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Packet for a message in a given channel.
 */
public interface ChannelMessage extends SendMessage {
    @Override @NotNull
    default PacketType getType() {
        return PacketType.SEND_MESSAGE;
    }

    /**
     * @return The {@link UUID} of this channel.
     */
    UUID getChannelUniqueId();
}
