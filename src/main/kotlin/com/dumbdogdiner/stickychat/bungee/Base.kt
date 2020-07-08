package com.dumbdogdiner.stickychat.bungee

interface Base {
    val plugin
        get() = StickyChatBungee.instance

    val proxy
        get() = plugin.proxy

    val logger
        get() = plugin.logger
}
