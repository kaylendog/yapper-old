package com.dumbdogdiner.stickychat.api;

import com.dumbdogdiner.stickychat.api.broadcast.BroadcastManager;
import com.dumbdogdiner.stickychat.api.broadcast.DeathMessageProvider;
import com.dumbdogdiner.stickychat.api.channel.ChannelManager;
import com.dumbdogdiner.stickychat.api.integration.Integration;
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager;
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageManager;
import com.dumbdogdiner.stickychat.api.messaging.Formatter;
import com.dumbdogdiner.stickychat.api.player.NicknameProvider;
import com.dumbdogdiner.stickychat.api.player.PlayerBlockManager;
import com.dumbdogdiner.stickychat.api.player.PriorityManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/** Represents a generic implementation of a chat system. */
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
    @NotNull
    Plugin getProvider();

    /**
     * Fetch the direct message manager.
     *
     * @return {@link DirectMessageManager}
     */
    @NotNull
    DirectMessageManager getDirectMessageManager();

    /**
     * Retrieve the nickname manager.
     *
     * @return The {@link NicknameProvider}
     */
    @NotNull
    NicknameProvider getNicknameManager();

    /**
     * Get the channel manager.
     *
     * @return The {@link ChannelManager}
     */
    @NotNull
    ChannelManager getChannelManager();

    /**
     * Get the formatter for the target player.
     *
     * @return The {@link Formatter} for the target player
     */
    @NotNull
    Formatter getFormatter();

    /**
     * Get the broadcast service.
     *
     * @return The {@link BroadcastManager}
     */
    @NotNull
    BroadcastManager getBroadcastService();

    /**
     * Get the death message service.
     *
     * @return The {@link DeathMessageProvider}
     */
    @NotNull
    DeathMessageProvider getDeathMessageProvider();

    /**
     * Get the integration manager.
     *
     * @return The {@link IntegrationManager}
     */
    @NotNull
    IntegrationManager getIntegrationManager();

    /**
     * Get the integration for the target plugin.
     *
     * @param plugin The target plugin
     * @return The {@link Integration} for the target plugin.
     */
    default @NotNull Integration getIntegration(Plugin plugin) {
        return this.getIntegrationManager().getIntegration(plugin);
    }

    /** @return The priority manager for this API implementation. */
    @NotNull
    PriorityManager getPriorityManager();

    /**
     * Fetch the priority level of the target player.
     *
     * @param target The target player
     * @return The player's current {@link Priority}.
     */
    default @NotNull Priority getPriority(Player target) {
        return this.getPriorityManager().getPriority(target);
    }

    /** @return The player blocking manager for this API implementation. */
    @NotNull
    PlayerBlockManager getPlayerBlockManager();
}
