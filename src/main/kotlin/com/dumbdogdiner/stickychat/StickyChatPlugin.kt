package com.dumbdogdiner.stickychat

import com.dumbdogdiner.stickychat.data.SqlManager
import com.dumbdogdiner.stickychat.files.Configuration
import com.dumbdogdiner.stickychat.utils.ServerUtils
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class StickyChatPlugin : JavaPlugin() {
    lateinit var sqlManager: SqlManager

    override fun onLoad() {
        instance = this
        Configuration.loadDefaultConfig()

        sqlManager = SqlManager()
    }

    override fun onEnable() {
        // Register commands

        // Register events

        // Plugin messaging
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        sqlManager.init()

        // Register PAPI expansion if available.
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            ServerUtils.log("Attached PlaceholderAPI extension.")
            PapiExpansion().register()
        } else {
            ServerUtils.log("PlaceholderAPI is not installed - skipping placeholder registration.")
        }

        ServerUtils.log("Done.")
    }

    override fun onDisable() { }

    companion object {
        lateinit var instance: StickyChatPlugin
    }
}
