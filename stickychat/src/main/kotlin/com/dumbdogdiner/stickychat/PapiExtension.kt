/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

class PapiExtension : com.dumbdogdiner.stickychat.WithPlugin, PlaceholderExpansion() {
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
            return this.plugin.nicknameProvider.getDisplayname(player)
        }
        return ""
    }
}
