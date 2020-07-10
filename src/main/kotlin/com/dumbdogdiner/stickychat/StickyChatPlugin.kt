package com.dumbdogdiner.stickychat

import com.dumbdogdiner.stickychat.commands.ChatManagementCommand
import com.dumbdogdiner.stickychat.commands.MailCommand
import com.dumbdogdiner.stickychat.commands.MessageCommand
import com.dumbdogdiner.stickychat.commands.NickCommand
import com.dumbdogdiner.stickychat.files.Configuration
import com.dumbdogdiner.stickychat.listeners.PlayerListener
import com.dumbdogdiner.stickychat.permissions.DefaultResolver
import com.dumbdogdiner.stickychat.permissions.LuckPermsResolver
import com.dumbdogdiner.stickychat.permissions.PermissionsResolver
import com.dumbdogdiner.stickychat.signspy.SignSpyManager
import com.dumbdogdiner.stickychat.utils.ServerUtils
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class StickyChatPlugin : JavaPlugin() {
    // lateinit var storageManager: StorageManager

    lateinit var permissionsResolver: PermissionsResolver
    lateinit var signSpyManager: SignSpyManager

    override fun onLoad() {
        instance = this
        Configuration.loadDefaultConfig()

        // Initialize storage manager
        // storageManager = StorageManager()
        // storageManager.init()

        signSpyManager = SignSpyManager()
    }

    override fun onEnable() {
        // Register permissions resolver
        permissionsResolver = if (LuckPermsResolver.isSupported()) {
            ServerUtils.log("Using LuckPerms for group resolution")
            LuckPermsResolver()
        } else {
            ServerUtils.log("Using default resolver for group resolution")
            DefaultResolver()
        }

        // Register commands
        getCommand("chat")?.setExecutor(ChatManagementCommand())
        getCommand("message")?.setExecutor(MessageCommand())
        getCommand("nick")?.setExecutor(NickCommand())
        getCommand("mail")?.setExecutor(MailCommand())

        // Register events
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(signSpyManager, this)

        // Plugin messaging
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        // Register PAPI expansion if available.
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            ServerUtils.log("Attached PlaceholderAPI extension")
            PapiExpansion().register()
        }
    }

    override fun onDisable() { }

    companion object {
        lateinit var instance: StickyChatPlugin
    }
}
