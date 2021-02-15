package com.dumbdogdiner.stickychat.api.messenger;

/**
 * Types of possible message packets.
 */
public enum MessageType {
    /**
     * Packet type for player messages in a channel.
     */
    SEND_MESSAGE,

    /**
     * Packet type for direct messages sent to players not found on the sender's server.
     */
    DIRECT_MESSAGE,

    /**
     * Packet type for players who have joined the network.
     */
    PLAYER_JOIN_NETWORK,

    /**
     * Packet type for players who have changed servers.
     */
    PLAYER_CHANGE_SERVER,

    /**
     * Packet type for channel creation.
     */
    CREATE_CHANNEL,

    /**
     * Packet type for channel deletion.
     */
    DELETE_CHANNEL
}
