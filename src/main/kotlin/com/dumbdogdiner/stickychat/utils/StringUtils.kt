package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import org.bukkit.ChatColor

object StringUtils : Base {
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
}
