package com.dumbdogdiner.stickychatbukkit.utils

import com.dumbdogdiner.stickychatbukkit.Base
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
    fun colorize(list: List<String>) = list.map {
        colorize(
            it
        )
    }

    /**
     * Format an SQL table name.
     */
    fun formatTableName(name: String): String {
        return "${config.getString("data.tableprefix", "stickychat_")}$name"
    }

    /**
     * Format a message into a staff chat message.
     */
    fun formatStaffChatMessage(from: Player, content: String): String {
        return PlaceholderUtils.setPlaceholdersSafe(
            from,
            colorize(config.getString("chat.staff-cat-format", "&e[SC] &8%name%: %message%")!!)
        )
            .replace("%name%", if (from.hasPermission("stickychat.colorizeNick")) colorize(
                    from.displayName
            ) else from.displayName)
            .replace("%message%", if (from.hasPermission("stickychat.colorizeMessage")) colorize(
                    content
            ) else content)
    }

    /**
     * Format a global chat message from a player.
     */
    fun formatGlobalChatMessage(from: Player, content: String): String {
        return PlaceholderUtils.setPlaceholdersSafe(
            from,
            colorize(config.getString("chat.format", "&8%name%: %message%")!!)
        )
            .replace("%name%", if (from.hasPermission("stickychat.colorizeNick")) colorize(
                from.displayName
            ) else from.displayName)
            .replace("%message%", if (from.hasPermission("stickychat.colorizeMessage")) colorize(
                content
            ) else content)
    }

    /**
     * Format an outgoing chat message from a given player.
     */
    fun formatOutgoingGlobalChatMessage(from: Player, content: String): String {
        return PlaceholderUtils.setPlaceholdersSafe(
            from,
            config.getString("chat.cross-server-format", "&8%name%: %message%")!!
        )
            .replace("%name%", if (from.hasPermission("stickychat.colorizeNick")) colorize(
                from.displayName
            ) else from.displayName)
            .replace("%message%", if (from.hasPermission("stickychat.colorizeMessage")) colorize(
                content
            ) else content)
    }

    /**
     * Format an incoming private message.
     */
    fun formatIncomingPrivateMessage(fromUuid: String, fromName: String, to: Player, content: String): String {
        val format = config.getString("messages.incoming.format", "&8[&e&lPM&r&8] &a%from_name% &8» &r%message%")!!
        val formatWithPlaceholders =
            PlaceholderUtils.setPlaceholdersSafe(to, format)

        return colorize(formatWithPlaceholders)
            .replace("%from_uuid%", fromUuid)
            .replace("%from_name%", fromName)
            .replace("%to_name%", to.name)
            .replace("%message%", content)
    }

    /**
     * Format an outgoing private message.
     */
    fun formatOutgoingPrivateMessage(from: Player, to: String, content: String): String {
        val format = config.getString("messages.outgoing.format", "&8[&e&lPM&r&8] &a%from_name% &8» &r%message%")!!
        val formatWithPlaceholders =
            PlaceholderUtils.setPlaceholdersSafe(from, format)

        return colorize(formatWithPlaceholders)
            .replace("%from_uuid%", from.uniqueId.toString())
            .replace("%from_name%", from.name)
            .replace("%to_name%", to)
            .replace("%message%", content)
    }

    const val entityName = "&cS&et&ai&bn&9k&dy"
}
