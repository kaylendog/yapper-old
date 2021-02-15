package com.dumbdogdiner.stickychat.api.messaging;

import com.dumbdogdiner.stickychat.api.Priority;
import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the switching between regular chat and staff chat, as well
 * as messages within staff chat.
 */
public interface StaffChatManager {
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
        return Bukkit.getOnlinePlayers()
            .stream()
            .filter(player -> priority.isGreaterThanOrEqualTo(StickyChat.getService().getPriority(from)))
            .collect(Collectors.toList());
    }

    /**
     * Check if this player has staff chat enabled. Returns true if that is the case.
     *
     * @return {@link Boolean}
     */
    @NotNull Boolean hasStaffChatEnabled();

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
     *
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
