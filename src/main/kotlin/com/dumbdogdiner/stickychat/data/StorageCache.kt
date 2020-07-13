package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import org.bukkit.entity.Player

/**
 * Caches player data to prevent database overhead.
 */
class StorageCache : Base {
    private val displayNameCache = HashMap<String, String>()

    /**
     * Whether the cache contains a user display name.
     */
    fun hasDisplayName(p: Player): Boolean {
        return displayNameCache.contains(p.uniqueId.toString())
    }

    /**
     * Fetch the display name of a player.
     */
    fun getPlayerDisplayName(p: Player): String {
        return if (displayNameCache.containsKey(p.uniqueId.toString())) {
            displayNameCache[p.uniqueId.toString()]!!
        } else {
            p.displayName
        }
    }
}
