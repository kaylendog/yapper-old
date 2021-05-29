/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/** Implements methods for sending packets between servers. */
public interface Messenger {
    /**
     * Send a packet to the target destination.
     *
     * @param packet The packet to send
     * @param destination The target destination
     */
    void sendPacket(Packet packet, RemoteServer destination);

    /**
     * Handle an incoming packet.
     *
     * @param packet The incoming packet
     * @param sender The remote server it came from
     */
    void receivePacket(Packet packet, RemoteServer sender);

    /**
     * Broadcast a packet to all remote servers.
     *
     * @param packet The packet to send
     */
    default void broadcastPacket(Packet packet) {
        for (RemoteServer server : this.getRemoteServers()) {
            this.sendPacket(packet, server);
        }
    }

    /** @return A list of available remote servers. */
    @NotNull
    List<RemoteServer> getRemoteServers();
}
