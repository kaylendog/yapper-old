package com.dumbdogdiner.stickychat.api.event;



import com.dumbdogdiner.stickychat.api.channel.Channel;
import java.awt.TextComponent;
import java.util.List;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** An event fired when a player sends a message in a channel. */
public class ChannelMessageEvent extends ChatEvent {
    private final Channel channel;
    private final Player sender;
    private final List<Player> recipients;
    private final TextComponent content;

    public ChannelMessageEvent(@NotNull Channel channel, @NotNull Player sender, @NotNull List<Player> recipients,
            @NotNull TextComponent content) {
        this.channel = channel;
        this.sender = sender;
        this.recipients = recipients;
        this.content = content;
    }

    /**
     * Get the channel the message was sent in.
     *
     * @return The {@link Channel} the message was sent in.
     */
    public Channel getChannel() {
        return this.channel;
    }

    /**
     * Get the player who sent the message.
     *
     * @return The {@link Player} who sent the message.
     */
    public Player getSender() {
        return this.sender;
    }

    /**
     * Get a list of players who received the message. This is not necessarily the list of players in this channel.
     *
     * @return A {@link List} of {@link Player}s who are the recipients of this message.
     */
    public List<Player> getRecipients() {
        return this.recipients;
    }

    /**
     * Get the content of this message.
     *
     * @return The {@link TextComponent} containing the message content.
     */
    public TextComponent getContent() {
        return this.content;
    }
}
