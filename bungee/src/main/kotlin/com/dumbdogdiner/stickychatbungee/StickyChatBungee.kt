package com.dumbdogdiner.stickychatbungee

import kr.entree.spigradle.annotations.PluginMain
import net.md_5.bungee.api.plugin.Plugin

@PluginMain
class StickyChatBungee : Plugin() {
    override fun onEnable() {
        logger.info("Registering plugin messaging channels...")
    }

    override fun onDisable() {
        logger.info("Unregistering plugin messaging channels...")
    }
}
