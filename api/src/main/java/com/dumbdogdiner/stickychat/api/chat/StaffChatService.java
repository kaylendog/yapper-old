package com.dumbdogdiner.stickychat.api.chat;

import com.dumbdogdiner.stickychat.api.Priority;
import com.dumbdogdiner.stickychat.api.StickyChat;
import com.dumbdogdiner.stickychat.api.util.WithPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the switching between regular chat and staff chat, as well
 * as messages within staff chat.
 */
public interface StaffChatService extends WithPlayer {
    /**
     * Get the player this service refers to.
     *
     * @return {@link Player}
     */
    @NotNull
    Player getPlayer();

    /**
     * Get the recipients of a staff chat message.
     *
     * @param from The sender of the message
     * @param priority The priority of the message
     * @return {@link List}
     */
    @NotNull
    static List<Player> getRecipients(@NotNull Player from, @NotNull Priority priority) {
        var recipients = new ArrayList<Player>();
        StickyChat.getService().getDataServices().forEach((data) -> {
            if (priority.isGreaterThan(data.getPriority()) && !data.getBlocked(from)) {
                recipients.add(data.getPlayer());
            }
        });
        return recipients;
    }

    /**
     * Check if this player has staff chat enabled. Returns true if that is the case.
     *
     * @return {@link Boolean}
     */
    @NotNull
    default Boolean hasStaffChatEnabled() {
        return this.getDataService().getStaffChatEnabled();
    }

    /**
     * Enable staff chat for this player. Returns false if the player already has staff chat
     * enabled.
     *
     * @return {@link Boolean}
     */
    @NotNull
    Boolean enableStaffChat();

    /**
     * Disable staff chat for this player. Returns false if the player does not have staff
     * chat enabled.
     * @return {@link Boolean}
     */
    @NotNull
    Boolean disableStaffChat();

    /**
     * Send a message in staff chat. Returns false if something went wrong.
     *
     * @param message The message to send.
     * @return {@link Boolean}
     */
    @NotNull
    Boolean sendStaffChatMessage(@NotNull  String message);
}
