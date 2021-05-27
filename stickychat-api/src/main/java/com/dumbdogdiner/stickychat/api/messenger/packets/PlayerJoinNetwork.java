/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import com.dumbdogdiner.stickychat.api.messenger.RemoteServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A packet sent when a player joins a server. */
public interface PlayerJoinNetwork extends PlayerChangeServer {
    @Override
    default @NotNull PacketType getType() {
        return PacketType.PLAYER_JOIN_NETWORK;
    }

    @Override
    @Nullable
    default RemoteServer getFrom() {
        return null;
    }
}
