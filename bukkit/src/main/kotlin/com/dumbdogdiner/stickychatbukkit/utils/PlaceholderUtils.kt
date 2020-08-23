package com.dumbdogdiner.stickychatbukkit.utils

import com.dumbdogdiner.stickychatbukkit.Base
import java.lang.Exception
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

        return try { PlaceholderAPI.setPlaceholders(player, str) } catch (err: Exception) {
            logger.warning("Error while parsing placeholders for player '${player.name} (${player.uniqueId})")
            err.printStackTrace()
            str
        }
    }

    fun checkPlayerIsSafe(player: Player): Boolean {
        return player.uniqueId.toString() != "ec294b17-377d-4bc5-80ee-fa0c56de77b9"
    }
}
