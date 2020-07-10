package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import org.bukkit.entity.Player

/**
 * Manages the storage of formatting data.
 */
interface StorageMethod : Base {
    /**
     * Initialize this manager.
     */
    abstract fun init()

    /**
     * Set the format string of the given group.
     */
    abstract fun setFormat(group: String, format: String): Boolean

    /**
     * Get the format string of the given group.
     */
    abstract fun getFormat(group: String): String

    /**
     * Get the format for a given player.
     */
    fun getUserFormat(player: Player): String {
        return getFormat(permissionsResolver.getPlayerGroup(player))
    }
}
