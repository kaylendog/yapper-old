package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object FormatUtils : Base {
    /**
     * Convert bukkit colour codes into their true values.
     */
    fun colorize(string: String) = ChatColor.translateAlternateColorCodes('&', string)

    /**
     * Convert a list of strings containing bukkit colour codes into their true values.
     */
    fun colorize(list: List<String>) = list.map { colorize(it); }

    /**
     * Format an SQL table name.
     */
    fun formatTableName(name: String): String {
        return "${config.getString("data.tableprefix", "stickychat_")}$name"
    }

    /**
     * Format a global chat message from a player.
     */
    fun formatGlobalChatMessage(from: Player, content: String): String {
        val content = content.replace("%", "")

        val withFormatting = colorize(config.getString("chat.format", "&8%name%: %message%")!!)
                .replace("%name%", if (from.hasPermission("stickychat.colorizeNick")) colorize(from.displayName) else from.displayName)
                .replace("%message%", if (from.hasPermission("stickychat.colorizeMessage")) colorize(content) else content)

        // This needs to be better
        return PlaceholderUtils.setPlaceholdersSafe(
                from,
                withFormatting
        )
    }

    /**
     * Format an incoming private message.
     */
    fun formatIncomingPrivateMessage(fromUuid: String, fromName: String, to: Player, content: String): String {
        val message = content.replace("%", "")

        return colorize(config.getString("private.incoming.format", "[PM] %from_name% => %message%")!!)
                .replace("%from_uuid%", fromUuid)
                .replace("%from_name%", fromName)
                .replace("%to_name%", to.name)
                .replace("%message%", message)
    }

    /**
     * Format an outgoing private message.
     */
    fun formatOutgoingPrivateMessage(from: Player, to: String, content: String): String {
        val message = content.replace("%", "")

        return colorize(config.getString("private.outgoing.format", "[PM] %from_name% => %message%")!!)
                .replace("%from_uuid%", from.uniqueId.toString())
                .replace("%from_name%", from.name)
                .replace("%to_name%", to)
                .replace("%message%", message)
    }

    const val entityName = "&cS&et&ai&bn&9k&dy"
}
