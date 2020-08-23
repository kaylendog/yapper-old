package com.dumbdogdiner.stickychatbukkit.data

import com.dumbdogdiner.stickychatbukkit.Base
import org.bukkit.entity.Player

/**
 * Caches player data to prevent database overhead.
 */
class StorageCache : Base {
    private val displayNameCache = HashMap<String, String>()

    /**
     * Whether the cache contains a user display name.
     */
    fun hasNickname(p: Player): Boolean {
        return displayNameCache.contains(p.uniqueId.toString())
    }

    /**
     * Fetch the display name of a player.
     */
    fun getPlayerNickname(p: Player): String? {
        return displayNameCache[p.uniqueId.toString()]
    }

    /**
     * Set a player's nickname.
     */
    fun setPlayerNickname(p: Player, v: String) {
        displayNameCache[p.uniqueId.toString()] = v
    }

    /**
     * Delete a display name from the cache.
     */
    fun deleteNickname(p: Player): Boolean {
        return displayNameCache.remove(p.uniqueId.toString()) != null
    }
}
