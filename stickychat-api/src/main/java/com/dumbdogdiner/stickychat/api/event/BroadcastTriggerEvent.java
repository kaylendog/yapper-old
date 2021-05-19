package com.dumbdogdiner.stickychat.api.event;

import com.dumbdogdiner.stickychat.api.broadcast.BroadcastTimer;
import lombok.Getter;

/**
 * An event emitted when a {@link BroadcastTimer} is triggered.
 */
public class BroadcastTriggerEvent extends ChatEvent {
	/**
	 * The timer that was created.
	 */
	@Getter
	private final BroadcastTimer timer;

	public BroadcastTriggerEvent(BroadcastTimer timer) {
		this.timer = timer;
	}
}
