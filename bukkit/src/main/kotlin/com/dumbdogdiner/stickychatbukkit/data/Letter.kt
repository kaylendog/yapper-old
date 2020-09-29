package com.dumbdogdiner.stickychatbukkit.data

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

/**
 * Represents a sent mail message.
 */
class Letter(
    val fromUuid: String,
    val fromName: String,
    val toUuid: String?,
    val toName: String,
    val title: String,
    val pages: List<String>,
    val createdAt: Long
) {
    fun asItem(): ItemStack {
        val item = ItemStack(Material.WRITTEN_BOOK, 1)
        val meta = item.itemMeta!! as BookMeta

        meta.title = title
        meta.author = fromName
        meta.pages = pages

        item.itemMeta = meta
        return item
    }
}
