/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messaging;

import com.dumbdogdiner.stickychat.api.Priority;
import org.jetbrains.annotations.Nullable;

/** An enum of possible return types from the {@link DirectMessageManager}. */
public enum DirectMessageResult {
    /** The message was sent and received successfully! */
    OK,
    /**
     * Could not send the message because this player has direct messages disabled.
     */
    FAIL_DISABLED,
    /** Could not send the message, as the target player has blocked the sender. */
    FAIL_BLOCK,
    /**
     * Could not send the message, as the target player has their priority set above {@link Priority#DEFAULT}.
     */
    FAIL_PRIORITY,
    /** Could not send the message, as the target player does not exist. */
    FAIL_NON_EXISTENT,
    /** Could not send the message, as the player tried to message themself. */
    FAIL_SELF,
    /** Could not send the message, as the sender is muted. */
    FAIL_MUTED,
    /** Could not send the message, as the sender is on cooldown. */
    FAIL_COOLDOWN,
    /** Could not send the message as the event was cancelled. */
    FAIL_EVENT_CANCELLED;

    private String message;

    /**
     * Get the custom message of this result.
     *
     * @return A {@link String} containing the message.
     */
    @Nullable
    public String getMessage() {
        return this.message;
    }

    /**
     * Create a direct message result with the target message.
     *
     * @param message The target message
     * @return A {@link DirectMessageResult} with an error message
     */
    public static DirectMessageResult withErrorMessage(String message) {
        var result = DirectMessageResult.FAIL_EVENT_CANCELLED;
        result.message = message;
        return result;
    };
}
