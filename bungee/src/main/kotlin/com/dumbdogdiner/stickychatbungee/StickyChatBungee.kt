package com.dumbdogdiner.stickychatbungee

import com.dumbdogdiner.stickychatcommon.Constants
import kr.entree.spigradle.annotations.PluginMain
import net.md_5.bungee.api.plugin.Plugin

@PluginMain
class StickyChatBungee : Plugin() {

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        logger.info("Registering plugin messaging channels...")
        proxy.pluginManager.registerListener(this, MessageForwarder)
        proxy.registerChannel(Constants.CHANNEL_NAME)
    }

    override fun onDisable() {
        logger.info("Unregistering plugin messaging channels...")
        proxy.unregisterChannel(Constants.CHANNEL_NAME)
        proxy.pluginManager.registerListener(this, MessageForwarder)
    }

    companion object {
        lateinit var instance: StickyChatBungee
    }
}
