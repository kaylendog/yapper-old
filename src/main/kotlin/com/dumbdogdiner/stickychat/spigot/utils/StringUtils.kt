package com.dumbdogdiner.stickychat.spigot.utils

import org.bukkit.ChatColor

object StringUtils {
    /**
     * Convert bukkit colour codes into their true values.
     */
    fun colorize(string: String) = ChatColor.translateAlternateColorCodes('&', string)

    /**
     * Convert a list of strings containing bukkit colour codes into their true values.
     */
    fun colorize(list: List<String>) = list.map { colorize(it); }
}