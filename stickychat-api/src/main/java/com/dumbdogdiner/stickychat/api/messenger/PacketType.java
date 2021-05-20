package com.dumbdogdiner.stickychat.api.messenger;
/** Types of possible message packets. */
public enum PacketType {
    /** Packet type for player messages in a channel. */
    SEND_MESSAGE,
    /**
     * Packet type for direct messages sent to players not found on the sender's server.
     */
    DIRECT_MESSAGE,
    /** Packet type for acknowledging a direct message has been received. */
    DIRECT_MESSAGE_ACK,
    /** Packet type for players who have changed servers. */
    PLAYER_CHANGE_SERVER,
    /** Packet type for players who have joined the network. */
    PLAYER_JOIN_NETWORK,
    /** Packet type for players who have left the network. */
    PLAYER_LEAVE_NETWORK,
    /** Packet type for channel creation. */
    CHANNEL_CREATE,
    /** Packet type for channel deletion. */
    CHANNEL_DELETE
}
