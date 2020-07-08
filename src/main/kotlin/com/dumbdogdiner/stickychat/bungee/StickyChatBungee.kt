package com.dumbdogdiner.stickychat.bungee

import com.dumbdogdiner.stickychat.Constants
import com.dumbdogdiner.stickychat.bungee.listeners.ChatForwader
import com.dumbdogdiner.stickychat.bungee.utils.ServerUtils
import net.md_5.bungee.api.plugin.Plugin

/**
 * BungeeCord plugin for enabling cross-server communication.
 */
class StickyChatBungee : Plugin() {

    override fun onLoad() {
        ServerUtils.log("Setting up...")
        instance = this
    }

    override fun onEnable() {
        ServerUtils.log("Attaching chat forwarders...")
        proxy.pluginManager.registerListener(this, ChatForwader())
        proxy.registerChannel(Constants.CHANNEL_NAME)

        ServerUtils.log("Done.")
    }

    companion object {
        lateinit var instance: StickyChatBungee
    }
}