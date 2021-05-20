package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/** A packet for sending direct messages between server. */
public interface DirectMessage extends SendMessage {
    @Override
    default @NotNull PacketType getType() {
        return PacketType.DIRECT_MESSAGE;
    }

    /**
     * Get the nonce for this direct message.
     *
     * @return A random {@link Integer}
     */
    Integer getNonce();

    /**
     * Get the UUID of the player who sent the message.
     *
     * @return The player's {@link UUID}
     */
    UUID getRecipientUniqueId();

    /**
     * Get the name of the player who sent the message.
     *
     * @return A {@link String} containing the player's name.
     */
    String getRecipientName();
}
