package com.dumbdogdiner.stickychat

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

/**
 * PlaceholderAPI Expansion class
 */
class PapiExpansion : PlaceholderExpansion(), Base {

    override fun persist(): Boolean {
        return true
    }

    override fun canRegister(): Boolean {
        return true
    }

    override fun getAuthor(): String {
        return plugin.description.authors.toString()
    }

    override fun getIdentifier(): String {
        return "parkour"
    }

    override fun getVersion(): String {
        return plugin.description.version
    }

    override fun onPlaceholderRequest(player: Player, identifier: String): String? {

        return "INVALID"
    }
}
