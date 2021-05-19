package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import org.jetbrains.annotations.NotNull;

/**
 * Packet for channel creation.
 */
public interface ChannelCreate extends Packet {
	@Override
	@NotNull default PacketType getType() {
		return PacketType.CHANNEL_CREATE;
	}
}
