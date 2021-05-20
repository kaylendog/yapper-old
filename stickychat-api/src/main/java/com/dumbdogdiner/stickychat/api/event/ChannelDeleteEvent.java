package com.dumbdogdiner.stickychat.api.event;



import com.dumbdogdiner.stickychat.api.Actor;
import com.dumbdogdiner.stickychat.api.channel.Channel;
import lombok.Getter;

/** An event emitted when a channel is deleted. */
public class ChannelDeleteEvent extends ChatEvent {
    /** The actor that created the channel. */
    @Getter
    private final Actor actor;

    /** The newly created channel. */
    @Getter
    private final Channel channel;

    public ChannelDeleteEvent(Actor actor, Channel channel) {
        this.actor = actor;
        this.channel = channel;
    }
}