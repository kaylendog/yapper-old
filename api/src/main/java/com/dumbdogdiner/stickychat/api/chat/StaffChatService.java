package com.dumbdogdiner.stickychat.api.chat;

import org.bukkit.entity.Player;

/**
 * Manages the switching between regular chat and staff chat, as well
 * as messages within staff chat.
 */
public interface StaffChatService {
    /**
     * Get the player this service refers to.
     *
     * @return {@link Player}
     */
    Player getPlayer();

    /**
     * Check if this player has staff chat enabled. Returns true if that is the case.
     *
     * @return {@link Boolean}
     */
    Boolean hasStaffChatEnabled();

    /**
     * Enable staff chat for this player. Returns false if the player already has staff chat
     * enabled.
     *
     * @return {@link Boolean}
     */
    Boolean enableStaffChat();

    /**
     * Disable staff chat for this player. Returns false if the player does not have staff
     * chat enabled.
     * @return {@link Boolean}
     */
    Boolean disableStaffChat();

    /**
     * Send a message in staff chat. Returns false if something went wrong.
     *
     * @param message The message to send.
     * @return {@link Boolean}
     */
    Boolean sendStaffChatMessage(String message);
}
