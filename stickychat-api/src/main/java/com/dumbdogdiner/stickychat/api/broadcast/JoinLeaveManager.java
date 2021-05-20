package com.dumbdogdiner.stickychat.api.broadcast;



import com.dumbdogdiner.stickychat.api.messenger.RemoteServer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/** Manages joins and leaves. */
public interface JoinLeaveManager {
    /**
     * Handle a player joining this server.
     *
     * @param player The player who joined
     */
    void handlePlayerJoin(@NotNull Player player);

    /**
     * Handle a player leaving this server.
     *
     * @param player The player who left
     */
    void handlePlayerLeave(@NotNull Player player);

    /**
     * Handle a player joining this server from another on the network.
     *
     * @param player The player who is joining
     * @param from The remote server which they are coming from
     */
    void handleIncomingPlayer(@NotNull Player player, @NotNull RemoteServer from);

    /**
     * Handle a player joining a remote server from this one.
     *
     * @param player The player who is leaving
     * @param to The remote server they are joining
     */
    void handleOutgoingPlayer(@NotNull Player player, @NotNull RemoteServer to);
}
