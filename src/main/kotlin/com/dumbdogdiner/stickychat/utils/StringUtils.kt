package com.dumbdogdiner.stickychat.utils

import org.bukkit.ChatColor

object StringUtils {
    /**
     * Colorize a string.
     */
    fun colorize(string: String) = ChatColor.translateAlternateColorCodes('&', string)

    /**
     * Colorize a list of strings.
     */
    fun colorize(message: List<String>): List<String> {
        val colorizedList = mutableListOf<String>()
        for (string in message) colorizedList.add(colorize(string))
        return colorizedList
    }
}