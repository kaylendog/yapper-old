/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
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
