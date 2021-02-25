package com.dumbdogdiner.stickychat.bukkit

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class PapiExtension : WithPlugin, PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "stickychat"
    }

    override fun getAuthor(): String {
        return this.plugin.description.authors.joinToString(", ")
    }

    override fun getVersion(): String {
        return this.plugin.description.version
    }

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        if (player == null) {
            return ""
        }

        if (params == "displayname") {
            return this.plugin.getNicknameService(player).displayname
        }
        return ""
    }
}
