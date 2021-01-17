package com.dumbdogdiner.stickychat.api.mail;

import org.bukkit.inventory.meta.BookMeta;

import java.util.UUID;

public interface Letter {
    /**
     * The unique ID of the player who sent this letter.
     * @return {@link UUID}
     */
    public UUID getSenderUniqueId();

    /**
     * The name of the player who sent this letter.
     * @return
     */
    public String getSenderName();

    /**
     * The unique ID of the player who is the recipient of this letter.
     * @return {@link UUID}
     */
    public UUID getRecipientUniqueId();

    /**
     * The name of the player who is the recipient of this letter.
     * @return {@link String}
     */
    public String getRecipientName();

    /**
     * Get the metadata of this letter.
     * TODO: Rod metadata stuff
     * @return {@link BookMeta}
     */
    public BookMeta getMetadata();

}
