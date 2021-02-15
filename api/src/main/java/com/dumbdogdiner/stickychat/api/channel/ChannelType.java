package com.dumbdogdiner.stickychat.api.channel;

/**
 * Enum of possible channel types.
 */
public enum ChannelType {
    /**
     * The default channel type - used by plugin implementations.
     */
    DEFAULT,

    /**
     * The global channel - there should only be one of these channels.
     */
    GLOBAL,

    /**
     * A group DM channel - there can be many instances of this type.
     */
    GROUP_DM,

    /**
     * A staff chat channel - there can be many instances of this type.
     */
    STAFF_CHAT
}
