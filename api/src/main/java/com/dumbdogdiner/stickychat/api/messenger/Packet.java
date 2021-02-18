package com.dumbdogdiner.stickychat.api.messenger;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet sent by the messenger.
 */
public interface Packet {
    /**
     * Get the type of this message packet.
     * @return The {@link PacketType} of this packet.
     */
    @NotNull PacketType getType();

    /**
     * Get the remote server this packet was received from.
     * @return A {@link RemoteServer}
     */
    @NotNull RemoteServer getRemoteServer();

    /**
     * Convert this packet into an encoded string.
     * @return A {@link String} containing Base64-encoded content.
     */
    @NotNull String toEncodedString();
}
