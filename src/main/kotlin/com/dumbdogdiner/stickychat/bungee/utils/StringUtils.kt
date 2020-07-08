package com.dumbdogdiner.stickychat.bungee.utils

import net.md_5.bungee.api.ChatColor

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