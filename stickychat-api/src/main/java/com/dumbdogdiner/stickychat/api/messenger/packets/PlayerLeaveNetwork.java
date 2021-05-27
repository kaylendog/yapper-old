/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import com.dumbdogdiner.stickychat.api.messenger.RemoteServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayerLeaveNetwork extends PlayerChangeServer {
    @Override
    default @NotNull PacketType getType() {
        return PacketType.PLAYER_LEAVE_NETWORK;
    }

    @Override
    @Nullable
    default RemoteServer getTo() {
        return null;
    }
}
