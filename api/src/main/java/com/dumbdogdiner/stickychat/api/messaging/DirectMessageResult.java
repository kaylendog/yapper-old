package com.dumbdogdiner.stickychat.api.messaging;

import com.dumbdogdiner.stickychat.api.Priority;

/**
 * An enum of possible return types from the {@link DirectMessageManager}.
 */
public enum DirectMessageResult {
    /**
     * The message was sent and received successfully!
     */
    OK,

    /**
     * Could not send the message because this player has direct messages disabled.
     */
    FAIL_DISABLED,

    /**
     * Could not send the message, as the target player has blocked the sender.
     */
    FAIL_BLOCK,

    /**
     * Could not send the message, as the target player has their priority set above {@link Priority#DEFAULT}.
     */
    FAIL_PRIORITY,

    /**
     * Could not send the message, as the target player does not exist.
     */
    FAIL_NONEXISTENT,

    /**
     * Could not send the message, as the player tried to message themself.
     */
    FAIL_SELF,

    /**
     * Could not send the message, as the sender is muted.
     */
    FAIL_MUTED,

    /**
     * Could not send the message, as the sender is on cooldown.
     */
    FAIL_COOLDOWN
}
