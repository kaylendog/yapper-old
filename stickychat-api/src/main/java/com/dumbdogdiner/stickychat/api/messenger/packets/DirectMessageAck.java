/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import org.jetbrains.annotations.NotNull;

/** Packet for acknowledging received direct messages. */
public interface DirectMessageAck extends Packet {
    @Override
    @NotNull
    default PacketType getType() {
        return PacketType.DIRECT_MESSAGE_ACK;
    }

    /**
     * Get the nonce of this packet.
     *
     * @return A random {@link Integer}
     */
    Integer getNonce();
}
