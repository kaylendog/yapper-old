package com.dumbdogdiner.stickychat.api.mail;

import com.dumbdogdiner.stickychat.api.Priority;

public enum MailResult {
    /** The message was sent and received successfully! */
    OK,
    /** Could not send the message, as the target player has blocked the sender. */
    FAIL_BLOCK,
    /**
     * Could not send the message, as the target player has their priority set above {@link Priority#DEFAULT}.
     */
    FAIL_PRIORITY,
    /** Could not send the message, as the player tried to message themself. */
    FAIL_SELF,
    /** Could not send the message, as the sender is muted. */
    FAIL_MUTED,
    /** Could not send the message, as the sender is on cooldown. */
    FAIL_COOLDOWN
}
