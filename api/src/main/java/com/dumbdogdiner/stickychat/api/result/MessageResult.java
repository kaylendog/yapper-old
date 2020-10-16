package com.dumbdogdiner.stickychat.api.result;

import com.dumbdogdiner.stickychat.api.chat.MessageService;

/**
 * An enum of possible return types from the {@link MessageService}.
 */
public enum MessageResult {
    /**
     * The message was sent and received successfully!
     */
    OK,

    /**
     * Could not send the message, as the sender is muted.
     */
    FAIL_MUTED,

    /**
     * Could not send the message, as the sender is on cooldown.
     */
    FAIL_COOLDOWN
}
