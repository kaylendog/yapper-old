/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.event;

import com.dumbdogdiner.stickychat.api.Actor;
import com.dumbdogdiner.stickychat.api.broadcast.BroadcastTimer;
import lombok.Getter;

/** An event emitted when a new {@link BroadcastTimer} is created. */
public class BroadcastCreateEvent extends ChatEvent {
    /** The actor that performed this action. */
    @Getter
    private final Actor actor;
    /** The timer that was created. */
    @Getter
    private final BroadcastTimer timer;

    public BroadcastCreateEvent(Actor actor, BroadcastTimer timer) {
        this.actor = actor;
        this.timer = timer;
    }
}
