package com.dumbdogdiner.stickychat.data

import org.bukkit.entity.Player

/**
 * Manages the storage of formatting data.
 */
interface StorageManager {
    /**
     * Initialize this manager.
     */
    fun init()

    /**
     * Set the format string of the given group.
     */
    fun setFormat(group: String, format: String): Boolean

    /**
     * Get the format string of the given group.
     */
    fun getFormat(group: String): String

    /**
     * Get the format for a given player.
     */
    fun getUserFormat(player: Player): String
}
