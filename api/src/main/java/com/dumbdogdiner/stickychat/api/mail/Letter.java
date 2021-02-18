package com.dumbdogdiner.stickychat.api.mail;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface Letter {
    /**
     * The unique ID of the player who sent this letter.
     * @return {@link UUID}
     */
    UUID getSenderUniqueId();

    /**
     * The name of the player who sent this letter.
     * @return {@link String}
     */
    String getSenderName();

    /**
     * The unique ID of the player who is the recipient of this letter.
     * @return {@link UUID}
     */
    UUID getRecipientUniqueId();

    /**
     * The name of the player who is the recipient of this letter.
     * @return {@link String}
     */
    String getRecipientName();

    /**
     * Get the content of this letter.
     * @return A {@link String} containing the content.
     */
    String getContent();

    // TODO: convert to book
}
