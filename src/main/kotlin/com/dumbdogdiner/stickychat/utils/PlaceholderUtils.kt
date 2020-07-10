package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

object PlaceholderUtils : Base {
    /**
     * Returns a boolean determining whether PlaceholderAPI is installed on the current server.
     */
    fun isPlaceholderApiPresent() = server.pluginManager.getPlugin("PlaceholderAPI") != null

    /**
     * Formats a string with placeholders if PAPI is installed, otherwise just return the string.
     */
    fun setPlaceholdersSafe(player: Player, str: String): String {
        if (!isPlaceholderApiPresent()) {
            return str
        }

        return PlaceholderAPI.setPlaceholders(player, str)
    }
}
