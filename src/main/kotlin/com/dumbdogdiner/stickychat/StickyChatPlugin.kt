package com.dumbdogdiner.stickychat

import com.dumbdogdiner.stickychat.chat.ChatManager
import com.dumbdogdiner.stickychat.commands.MessageCommand
import com.dumbdogdiner.stickychat.data.StorageManager
import com.dumbdogdiner.stickychat.data.sql.PostgresManager
import com.dumbdogdiner.stickychat.files.Configuration
import com.dumbdogdiner.stickychat.permissions.DefaultResolver
import com.dumbdogdiner.stickychat.permissions.LuckPermsResolver
import com.dumbdogdiner.stickychat.permissions.PermissionsResolver
import com.dumbdogdiner.stickychat.utils.ServerUtils
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class StickyChatPlugin : JavaPlugin() {
    lateinit var chatManager: ChatManager
    lateinit var storageManager: StorageManager

    lateinit var permissionsResolver: PermissionsResolver

    override fun onLoad() {
        instance = this
        Configuration.loadDefaultConfig()

        chatManager = ChatManager()
        storageManager = PostgresManager()
    }

    override fun onEnable() {
        // Register commands
        getCommand("message")?.setExecutor(MessageCommand())

        // Register events

        // Plugin messaging
        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        storageManager.init()

        // Register PAPI expansion if available.
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            ServerUtils.log("Attached PlaceholderAPI extension")
            PapiExpansion().register()
        } else {
            ServerUtils.log("PlaceholderAPI is not installed - skipping placeholder registration")
        }

        // Register permissions resolver
        permissionsResolver = if (LuckPermsResolver.isSupported()) {
            ServerUtils.log("Using LuckPerms for group resolution")
            LuckPermsResolver()
        } else {
            ServerUtils.log("Using defaut resolver for group resolution")
            DefaultResolver()
        }

        // Initialize the chat manager after the group] resolver has been initialized
        chatManager.init()

        ServerUtils.log("Done.")
    }

    override fun onDisable() { }

    companion object {
        lateinit var instance: StickyChatPlugin
    }
}
