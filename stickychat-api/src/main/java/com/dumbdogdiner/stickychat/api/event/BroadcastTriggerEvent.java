/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.event;

import com.dumbdogdiner.stickychat.api.broadcast.BroadcastTimer;
import lombok.Getter;

/** An event emitted when a {@link BroadcastTimer} is triggered. */
public class BroadcastTriggerEvent extends ChatEvent {
    /** The timer that was created. */
    @Getter
    private final BroadcastTimer timer;

    public BroadcastTriggerEvent(BroadcastTimer timer) {
        this.timer = timer;
    }
}
