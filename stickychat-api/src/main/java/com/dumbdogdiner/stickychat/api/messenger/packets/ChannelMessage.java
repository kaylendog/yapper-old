package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/** Packet for a message in a given channel. */
public interface ChannelMessage extends SendMessage {
    @Override
    @NotNull
    default PacketType getType() {
        return PacketType.SEND_MESSAGE;
    }

    /** @return The {@link UUID} of this channel. */
    UUID getChannelUniqueId();
}
