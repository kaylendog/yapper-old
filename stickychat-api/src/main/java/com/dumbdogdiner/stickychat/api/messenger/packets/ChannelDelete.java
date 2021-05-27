/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/** Packet for channel deletion. */
public interface ChannelDelete extends Packet, ActorEvent {
    @Override
    @NotNull
    default PacketType getType() {
        return PacketType.CHANNEL_DELETE;
    }

    /** @return The unique ID of the channel that was deleted. */
    UUID getChannelUniqueID();

    /** @return A reason for channel deletion. */
    String getReason();
}
