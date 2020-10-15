package com.dumbdogdiner.stickychatapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents a generic implementation of a chat system.
 */
public interface ChatService {
    /**
     * Register the chat service.
     * @param plugin The plugin registering the service
     * @param service The plugin's implementation of the service
     */
    static void registerService(JavaPlugin plugin, ChatService service) {
        Bukkit.getServicesManager().register(ChatService.class, service, plugin, ServicePriority.Lowest);
    }

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
     * Fetch the message service for the target player.
     * If this does not already exist, it should be created. See
     * {@link MessageService} for more information.
     *
     * @param player The player of the message service to get
     * @return {@link MessageService}
     */
    MessageService getMessageService(Player player);

    /**
     * Fetch the direct message service for the target player.
     * If this does not already exist, it should be created. See
     * {@link DirectMessageService} for more information.
     *
     * @param player The player of the DM service to get
     * @return {@link DirectMessageService}
     */
    DirectMessageService getDirectMessageService(Player player);

    /**
     * Get the formatter of this chat service.
     * @return {@link Formatter}
     */
    Formatter getFormatter();

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

