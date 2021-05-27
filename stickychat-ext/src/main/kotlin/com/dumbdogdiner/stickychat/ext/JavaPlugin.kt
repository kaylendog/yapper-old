/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.ext

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/**
 * The integration for this plugin
 */
val JavaPlugin.chatIntegration
    get() = chat.getIntegration(this)

/**
 * Register an event listener
 */
fun JavaPlugin.registerListener(vararg listeners: Listener) {
    listeners.forEach {
        Bukkit.getPluginManager().registerEvents(it, this)
    }
}
