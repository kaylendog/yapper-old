package com.dumbdogdiner.stickychat.api.messenger;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet sent by the messenger.
 */
public interface Message {
    /**
     * Get the type of this message packet.
     * @return The {@link MessageType} of this packet.
     */
    @NotNull MessageType getType();

    /**
     * Convert this packet into an encoded string.
     * @return A {@link String} containing Base64-encoded content.
     */
    @NotNull String toEncodedString();
}
