/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
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
