package com.dumbdogdiner.stickychatapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Represents a generic implementation of a chat system.
 */
public interface ChatService {
    /**
     * Fetch the instantiated chat service object.
     * @return {@link ChatService}
     */
    static ChatService getService() {
        var provider = Bukkit.getServicesManager().getRegistration(ChatService.class);
        // just in case someone tries something wacky.
        if (provider == null) {
            throw new RuntimeException("Failed to fetch ChatService - running service is invalid!");
        }
        return provider.getProvider();
    }

    /**
     * Fetch the direct message service for the target player.
     * If this does not already exist, it should be created. See
     * {@link DirectMessageService} for
     * more information.
     * @param player The player the DM service refers to
     * @return {@link DirectMessageService}
     */
    DirectMessageService getDirectMessageService(Player player);

    /**
     * Get the priority of the target player.
     * @param player The target player
     * @return {@link Priority}
     */
    Priority getPriority(Player player);

    /**
     * Set the priority of the target player.
     * @param player The target player
     * @param priority Their new priority
     */
    void setPriority(Player player, Priority priority);
}

