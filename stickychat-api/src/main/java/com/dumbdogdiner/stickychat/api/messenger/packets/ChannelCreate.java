/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import org.jetbrains.annotations.NotNull;

/** Packet for channel creation. */
public interface ChannelCreate extends Packet, ActorEvent {
    @Override
    @NotNull
    default PacketType getType() {
        return PacketType.CHANNEL_CREATE;
    }
}
