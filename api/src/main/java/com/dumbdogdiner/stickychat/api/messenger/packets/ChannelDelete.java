package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.messenger.Packet;
import com.dumbdogdiner.stickychat.api.messenger.PacketType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Packet for channel deletion.
 */
public interface ChannelDelete extends Packet {
	@Override
	@NotNull default PacketType getType() {
		return PacketType.CHANNEL_DELETE;
	}

	/**
	 * @return The unique ID of the channel that was deleted.
	 */
	UUID getChannelUniqueID();

	/**
	 * @return A reason for channel deletion.
	 */
	String getReason();

	/**
	 * @return The unique ID of the actor that performed this action.
	 */
	UUID getActorUniqueId();

	/**
	 * @return The name of the actor that performed this action.
	 */
	String getActorName();
}
