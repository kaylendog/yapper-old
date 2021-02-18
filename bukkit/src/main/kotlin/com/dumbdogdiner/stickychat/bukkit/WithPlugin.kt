package com.dumbdogdiner.stickychat.bukkit

import org.bukkit.Bukkit
import org.bukkit.event.Event

interface WithPlugin {
    val plugin
        get() = StickyChatPlugin.plugin

    /**
     * The plugin logger.
     */
    val logger
        get() = this.plugin.logger

    /**
     * The plugin config.
     */
    val config
        get() = this.plugin.config

    /**
     * The integration used by the plugin implementation.
     */
    val integration
        get() = this.plugin.integrationManager.getIntegration(this.plugin)

    /**
     * Short-hand to quickly call a bukkit event.
     */
    fun callBukkitEvent(event: Event) { Bukkit.getServer().pluginManager.callEvent(event) }
}
