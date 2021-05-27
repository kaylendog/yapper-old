package com.dumbdogdiner.stickychat.api.player;

import com.dumbdogdiner.stickychat.api.StickyChat;
import com.dumbdogdiner.stickychat.api.channel.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.Player;

/**
 * Handles the storing of player mutes.
 */
public interface MuteManager {
    /**
     * Fetch a list of channels the target player player is muted in.
     *
     * @param player The target player
     * @return A {@link List} of {@link Channel}s
     */
    default List<Channel> getMutedChannels(Player player) {
        // get a list of all channels
        List<Channel> channels = StickyChat.getService().getChannelManager().getChannels();
        List<Channel> mutedChannels = new ArrayList<>();
        // iterate over channels and check if muted
        for (Channel channel : channels) {
            if (channel.isMuted(player)) {
                mutedChannels.add(channel);
            }
        }
        return mutedChannels;
    }

    /**
     * Fetch a hashmap of all existing mutes.
     */
    default HashMap<Channel, List<Player>> getMutes() {
        List<Channel> channels = StickyChat.getService().getChannelManager().getChannels();
        HashMap<Channel, List<Player>> mutes = new HashMap<>();
        // iterate over channels and add muted players to hashmap
        for (Channel channel : channels) {
            mutes.put(channel, channel.getMutedPlayers());
        }
        return mutes;
    }
}
