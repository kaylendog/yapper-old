package com.dumbdogdiner.stickychatbukkit

import org.bukkit.Bukkit

/**
 * this is hot~
 * will always love jcx for this idea
 */
interface Base {
    val plugin
        get() = StickyChatBukkit.instance

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

    val mailManager
        get() = plugin.mailManager

    val privateMessageManager
        get() = plugin.privateMessageManager

    val signSpyManager
        get() = plugin.signSpyManager

    val staffChatManager
        get() = plugin.staffChatManager

    val messenger
        get() = PluginMessenger
}
