package com.dumbdogdiner.stickychat.api.event;

import com.dumbdogdiner.stickychat.api.Actor;
import com.dumbdogdiner.stickychat.api.channel.Channel;

/**
 * An event emitted when a channel is created.
 */
public class ChannelCreateEvent extends ChatEvent {
	/**
	 * @return The channel being created.
	 */
	Channel getChannel();

	/**
	 * @return The actor that performed this action.
	 */
	Actor getActor();
}
