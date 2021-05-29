/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger.packets;

import com.dumbdogdiner.stickychat.api.Actor;
import org.jetbrains.annotations.NotNull;

/** Represents an event that has an actor. */
public interface ActorEvent {
    /** @return The actor that triggered this event. */
    @NotNull
    Actor getActor();
}
