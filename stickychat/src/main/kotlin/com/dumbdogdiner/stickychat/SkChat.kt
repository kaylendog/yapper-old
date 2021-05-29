/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat

import com.dumbdogdiner.stickychat.ext.registerListener
import com.dumbdogdiner.stickychat.listener.ReloadListener
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIConfig
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class SkChat : JavaPlugin() {
    companion object {
        // store a singleton reference to the plugin
        lateinit var instance: SkChat
    }

    override fun onLoad() {
        // set singleton reference
        instance = this
        // enable CommandAPI
        val config = CommandAPIConfig()
        config.verboseOutput = false
        CommandAPI.onLoad(config)
        // register event listeners
        this.registerListener(ReloadListener())
    }

    override fun onEnable() {
        CommandAPI.onEnable(this)
    }
}
