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
    fun init()

    /**
     * Get all group formats.
     */
    fun getAllGroupFormats(): HashMap<String, String>

    /**
     * Set the format string of the given group.
     */
    fun setGroupFormat(group: String, format: String): Boolean

    /**
     * Get the format string of the given group.
     */
    fun getGroupFormat(group: String): String?

    /**
     * Get the format for a given player.
     */
    fun getUserFormat(player: Player): String? {
        return getGroupFormat(permissionsResolver.getPlayerGroup(player))
    }
}
