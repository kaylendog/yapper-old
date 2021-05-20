package com.dumbdogdiner.stickychat.api.channel;

/** Enum of possible channel types. */
public enum ChannelType {
    /** The default channel type - there can be many instances of this type. */
    DEFAULT,

    /**
     * The global channel, which receives messages from other servers - there should only be one of these channels.
     */
    GLOBAL,

    /**
     * The server channel, isolated to the specific server instance the plugin is running on - there should only be one
     * of these channels.
     */
    SERVER,

    /** A staff chat channel - there can be many instances of this type. */
    STAFF_CHAT
}
