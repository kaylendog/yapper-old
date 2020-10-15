package com.dumbdogdiner.stickychatapi;

import com.dumbdogdiner.stickychatapi.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Represents a generic implementation of a chat system.
 */
public interface StickyChat {
    /**
     * Register the chat service.
     * @param plugin The plugin registering the service
     * @param service The plugin's implementation of the service
     */
    static void registerService(JavaPlugin plugin, StickyChat service) {
        Bukkit.getServicesManager().register(StickyChat.class, service, plugin, ServicePriority.Lowest);
    }

    /**
     * Fetch the instantiated chat service object.
     * @return {@link StickyChat}
     */
    static StickyChat getService() {
        var provider = Bukkit.getServicesManager().getRegistration(StickyChat.class);
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
     * Get the staff chat service for the target player.
     * If this does not exist already, and the player has permission
     * to use staff chat, it should be created. See
     * {@link StaffChatService} for more information.
     *
     * @param player The player of the SC service to get
     * @return {@link StaffChatService}
     */
    StaffChatService getStaffChatService(Player player);

    /**
     * Get the data service for the target player. If this does
     * not already exist, it should be created.
     *
     * @param player The player who's data service to get
     * @return {@link DataService}
     */
    DataService getDataService(Player player);

    /**
     * Return all data services for all cached players.
     *
     * @return {@link List}
     */
    List<DataService> getDataServices();

    /**
     * Get the formatter of this chat service.
     * @return {@link Formatter}
     */
    Formatter getFormatter();

    /**
     * Disable chat globally. Returns true if successful.
     *
     * @return {@link Boolean}
     */
    Boolean disableChat();

    /**
     * Enable chat globally. Returns true if successful.
     *
     * @return {@link Boolean}
     */
    Boolean enableChat();
}

