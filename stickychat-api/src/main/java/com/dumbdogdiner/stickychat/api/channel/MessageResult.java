/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.channel;
/**
 * An enum of possible return types when sending a message in a {@link Channel}.
 */
public enum MessageResult {
    /** The message was sent and received successfully! */
    OK,
    /** Could not send the message, as the sender is muted. */
    FAIL_MUTED,
    /** Could not send the message, as the sender is on cooldown. */
    FAIL_COOLDOWN
}
