package com.dumbdogdiner.stickychat.api;

import com.dumbdogdiner.stickychat.api.channel.ChannelManager;
import com.dumbdogdiner.stickychat.api.broadcast.DeathMessageProvider;
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageManager;
import com.dumbdogdiner.stickychat.api.integration.Integration;
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager;
import com.dumbdogdiner.stickychat.api.broadcast.BroadcastManager;
import com.dumbdogdiner.stickychat.api.player.NicknameProvider;
import com.dumbdogdiner.stickychat.api.player.PlayerBlockManager;
import com.dumbdogdiner.stickychat.api.player.PriorityManager;
import com.dumbdogdiner.stickychat.api.messaging.StaffChatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a generic implementation of a chat system.
 */
public interface StickyChat {
    /**
     * Register the chat service.
     *
     * @param plugin The plugin registering the service
     * @param service The plugin's implementation of the service
     */
    static void registerService(JavaPlugin plugin, StickyChat service) {
        Bukkit.getServicesManager().register(StickyChat.class, service, plugin, ServicePriority.Lowest);
    }

    /**
     * Fetch the instantiated chat service object.
     *
     * @return {@link StickyChat}
     */
    static @NotNull StickyChat getService() {
        var provider = Bukkit.getServicesManager().getRegistration(StickyChat.class);
        // just in case someone tries something wacky.
        if (provider == null) {
            throw new RuntimeException("Failed to fetch ChatService - running service is invalid!");
        }
        return provider.getProvider();
    }

    /**
     * Return a reference to the plugin providing the StickyChat implementation.
     *
     * @return The {@link Plugin} implementing StickyChat.
     */
    @NotNull Plugin getProvider();

    /**
     * Fetch the direct message manager.
     *
     * @return {@link DirectMessageManager}
     */
    @NotNull DirectMessageManager getDirectMessageManager();

    /**
     * Get the staff chat service.
     *
     * @return {@link StaffChatManager}
     */
    @NotNull StaffChatManager getStaffChatManager();

    /**
     * Retrieve the nickname manager.
     *
     * @return The {@link NicknameProvider}
     */
    @NotNull NicknameProvider getNicknameManager();

    /**
     * Get the channel manager.
     *
     * @return The {@link ChannelManager}
     */
    @NotNull ChannelManager getChannelManager();

    /**
     * Get the formatter for the target player.
     *
     * @param player The target player
     * @return The {@link Formatter} for the target player
     */
    @NotNull Formatter getFormatter(Player player);

    /**
     * Get the broadcast service.
     *
     * @return The {@link BroadcastManager}
     */
    @NotNull BroadcastManager getBroadcastService();

    /**
     * Get the death message service.
     *
     * @return The {@link DeathMessageProvider}
     */
    @NotNull DeathMessageProvider getDeathMessageManager();

    /**
     * Get the integration manager.
     * @return The {@link IntegrationManager}
     */
    @NotNull IntegrationManager getIntegrationManager();

    /**
     * Get the integration for the target plugin.
     *
     * @param plugin The target plugin
     * @return The {@link Integration} for the target plugin.
     */
    default @NotNull Integration getIntegration(Plugin plugin) {
        return this.getIntegrationManager().getIntegration(plugin);
    }

    /**
     * Disable chat globally.
     *
     * @return True if the action was successful.
     */
    @NotNull Boolean disableChat();

    /**
     * Enable chat globally.
     *
     * @return True if the action was successful.
     */
    @NotNull Boolean enableChat();

    /**
     * @return The priority manager for this API implementation.
     */
    @NotNull PriorityManager getPriorityManager();

    /**
     * Fetch the priority level of the target player.
     *
     * @param target The target player
     * @return The player's current {@link Priority}.
     */
    default @NotNull Priority getPriority(Player target) {
        return this.getPriorityManager().getPriority(target);
    }

    /**
     * @return The player blocking manager for this API implementation.
     */
    @NotNull PlayerBlockManager getPlayerBlockManager();

    /**
     * Test if the given player has blocked the target player.
     * @param player The player
     * @param target The player who's blocking state is being tested
     * @return True if the player has blocked the target.
     */
    default @NotNull Boolean hasPlayerBlocked(Player player, Player target) {
        return this.getPlayerBlockManager().hasPlayerBlocked(player, target);
    }

    /**
     * Test if a player is able to message another.
     * @param player The player
     * @param target The player they are trying to message
     * @param priority The priority level with which  the message is being sent.
     * @return True if they can send a message.
     */
    default @NotNull Boolean playerCanMessageTarget(Player player, Player target, Priority priority) {
        return priority.isGreaterThan(this.getPriority(target)) && !this.hasPlayerBlocked(player, target) && !this.hasPlayerBlocked(target, player);
    }
}
