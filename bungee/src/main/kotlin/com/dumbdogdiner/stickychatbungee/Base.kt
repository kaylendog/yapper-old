package com.dumbdogdiner.stickychatbungee

import net.md_5.bungee.api.ProxyServer

interface Base {
    val plugin
        get() = StickyChatBungee.instance

    val proxy
        get() = ProxyServer.getInstance()

    val logger
        get() = plugin.logger
}
