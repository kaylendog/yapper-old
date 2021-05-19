package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import com.dumbdogdiner.stickychat.api.messenger.RemoteServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A packet sent when a player joins a server.
 */
public interface PlayerJoinNetwork extends PlayerChangeServer {
	@Override
	default @NotNull PacketType getType() {
		return PacketType.PLAYER_JOIN_NETWORK;
	}

	@Override
	@Nullable default RemoteServer getFrom() {
		return null;
	}
}
