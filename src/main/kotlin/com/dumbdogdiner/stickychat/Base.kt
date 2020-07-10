package com.dumbdogdiner.stickychat

import org.bukkit.Bukkit

/**
 * this is hot~
 * will always love jcx for this idea
 */
interface Base {
    val plugin
        get() = StickyChatPlugin.instance

    val server
        get() = Bukkit.getServer()

    val config
        get() = plugin.config

    val logger
        get() = plugin.logger

    val storageManager
        get() = plugin.storageManager

    val chatManager
        get() = plugin.chatManager

    val permissionsResolver
        get() = plugin.permissionsResolver
}
