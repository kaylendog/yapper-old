/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat

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
