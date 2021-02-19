package com.dumbdogdiner.stickychat.api.messenger;

/**
 * Implements methods for sending packets between servers.
 */
public interface Messenger {
    /**
     * Send a packet to the target destination.
     * @param packet The packet to send
     * @param destination The target destination
     */
    void sendPacket(Packet packet, RemoteServer destination);

    /**
     * Handle an incoming packet.
     * @param packet The incoming packet
     * @param sender The remote server it came from
     */
    void receivePacket(Packet packet, RemoteServer sender);
}
