package com.dumbdogdiner.stickychat.api.messenger.packets;



import com.dumbdogdiner.stickychat.api.Actor;
import org.jetbrains.annotations.NotNull;

/** Represents an event that has an actor. */
public interface ActorEvent {
    /** @return The actor that triggered this event. */
    @NotNull
    Actor getActor();
}
