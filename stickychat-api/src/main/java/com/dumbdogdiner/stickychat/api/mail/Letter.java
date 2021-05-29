/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.mail;

import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public interface Letter {
    /**
     * The unique ID of the player who sent this letter.
     *
     * @return {@link UUID}
     */
    UUID getSenderUniqueId();

    /**
     * The name of the player who sent this letter.
     *
     * @return {@link String}
     */
    String getSenderName();

    /**
     * The unique ID of the player who is the recipient of this letter.
     *
     * @return {@link UUID}
     */
    UUID getRecipientUniqueId();

    /**
     * The name of the player who is the recipient of this letter.
     *
     * @return {@link String}
     */
    String getRecipientName();

    /**
     * Get the content of this letter.
     *
     * @return A {@link String} containing the content.
     */
    String getContent();

    // TODO: convert to book
    BookMeta getMetadata();

    /**
     * Convert this letter into a book.
     *
     * @return An {@link ItemStack} of type book.
     */
    default ItemStack toItemStack() {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        item.setItemMeta(this.getMetadata());
        return item;
    }
}
