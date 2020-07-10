package com.dumbdogdiner.stickychat

import com.dumbdogdiner.stickychat.commands.MailCommand
import com.dumbdogdiner.stickychat.commands.MessageCommand
import com.dumbdogdiner.stickychat.commands.NickCommand
import com.dumbdogdiner.stickychat.data.StorageManager
import com.dumbdogdiner.stickychat.files.Configuration
import com.dumbdogdiner.stickychat.listeners.PlayerListener
import com.dumbdogdiner.stickychat.permissions.DefaultResolver
import com.dumbdogdiner.stickychat.permissions.LuckPermsResolver
import com.dumbdogdiner.stickychat.permissions.PermissionsResolver
import com.dumbdogdiner.stickychat.utils.ServerUtils
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class StickyChatPlugin : JavaPlugin() {
    lateinit var storageManager: StorageManager

    lateinit var permissionsResolver: PermissionsResolver

    override fun onLoad() {
        instance = this
        Configuration.loadDefaultConfig()

        // Initialize storage manager
        storageManager = StorageManager()
        storageManager.init()

        // Register permissions resolver
        permissionsResolver = if (LuckPermsResolver.isSupported()) {
            ServerUtils.log("Using LuckPerms for group resolution")
            LuckPermsResolver()
        } else {
            ServerUtils.log("Using default resolver for group resolution")
            DefaultResolver()
        }
    }

    override fun onEnable() {
        // Register commands
        getCommand("message")?.setExecutor(MessageCommand())
        getCommand("nick")?.setExecutor(NickCommand())
        getCommand("mail")?.setExecutor(MailCommand())

        // Register events
        server.pluginManager.registerEvents(PlayerListener(), this)

        // Plugin messaging
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        // Register PAPI expansion if available.
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            ServerUtils.log("Attached PlaceholderAPI extension")
            PapiExpansion().register()
        }

        ServerUtils.log("Done.")
    }

    override fun onDisable() { }

    companion object {
        lateinit var instance: StickyChatPlugin
    }
}
