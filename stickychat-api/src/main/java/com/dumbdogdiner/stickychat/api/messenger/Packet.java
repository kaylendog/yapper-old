/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger;

import org.jetbrains.annotations.NotNull;

/** Represents a packet sent by the messenger. */
public interface Packet {
    /**
     * Get the type of this message packet.
     *
     * @return The {@link PacketType} of this packet.
     */
    @NotNull
    PacketType getType();

    /** @return The {@link RemoteServer} this packet was received from. */
    @NotNull
    RemoteServer getDestination();

    /** @return The {@link RemoteServer} this packet was sent from. */
    @NotNull
    RemoteServer getOrigin();

    /**
     * Convert this packet into an encoded string.
     *
     * @return A {@link String} containing Base64-encoded content.
     */
    @NotNull
    String toEncodedString();
}
