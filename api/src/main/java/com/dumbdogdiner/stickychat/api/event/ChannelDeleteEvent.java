package com.dumbdogdiner.stickychat.api.event;

import com.dumbdogdiner.stickychat.api.Actor;
import com.dumbdogdiner.stickychat.api.channel.Channel;
import org.jetbrains.annotations.NotNull;

/**
 * An event emitted when a channel is deleted.
 */
public class ChannelDeleteEvent extends ChatEvent {
	/**
	 * @return The channel being deleted.
	 */
	@NotNull Channel getChannel();

	/**
	 * @return The actor responsible for this action.
	 */
	@NotNull Actor getActor();
}
