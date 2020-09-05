package com.dumbdogdiner.stickychatbukkit

import com.dumbdogdiner.stickychatbukkit.commands.ChatCommand
import com.dumbdogdiner.stickychatbukkit.commands.MailCommand
import com.dumbdogdiner.stickychatbukkit.commands.MessageCommand
import com.dumbdogdiner.stickychatbukkit.commands.NickCommand
import com.dumbdogdiner.stickychatbukkit.commands.ReplyCommand
import com.dumbdogdiner.stickychatbukkit.data.StorageManager
import com.dumbdogdiner.stickychatbukkit.files.DeathMessages
import com.dumbdogdiner.stickychatbukkit.listeners.DeathListener
import com.dumbdogdiner.stickychatbukkit.listeners.PlayerListener
import com.dumbdogdiner.stickychatbukkit.listeners.SignListener
import com.dumbdogdiner.stickychatbukkit.managers.ChatManager
import com.dumbdogdiner.stickychatbukkit.managers.MailManager
import com.dumbdogdiner.stickychatbukkit.managers.PrivateMessageManager
import com.dumbdogdiner.stickychatbukkit.managers.SignSpyManager
import com.dumbdogdiner.stickychatcommon.Constants
import org.bukkit.plugin.java.JavaPlugin

class StickyChatBukkit : JavaPlugin() {
    lateinit var storageManager: StorageManager

    lateinit var chatManager: ChatManager
    lateinit var mailManager: MailManager
    lateinit var privateMessageManager: PrivateMessageManager
    lateinit var signSpyManager: SignSpyManager

    // :3
    var heckDontEnableSomethingWentWrong = false

    override fun onLoad() {
        instance = this

        logger.info("Loading configuration...")
        saveDefaultConfig()
        reloadConfig()

        DeathMessages.loadMessages()

        // Initialize storage manager
        storageManager = StorageManager()
        storageManager.init()

        chatManager = ChatManager()
        mailManager = MailManager()
        privateMessageManager = PrivateMessageManager()
        signSpyManager = SignSpyManager()
    }

    override fun onEnable() {
        if (heckDontEnableSomethingWentWrong) {
            return
        }

        // Register commands
        getCommand("stickychat")?.setExecutor(ChatCommand())
        getCommand("message")?.setExecutor(MessageCommand())
        getCommand("reply")?.setExecutor(ReplyCommand())
        getCommand("nick")?.setExecutor(NickCommand())
        getCommand("mail")?.setExecutor(MailCommand())

        // Register events
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(SignListener(), this)
        server.pluginManager.registerEvents(DeathListener(), this)

        // Plugin messaging
        server.messenger.registerOutgoingPluginChannel(this, Constants.CHANNEL_NAME)
        server.messenger.registerIncomingPluginChannel(this, Constants.CHANNEL_NAME,
            PluginMessenger
        )
        logger.info("Registered cross-server communication channels")

        // Register PAPI expansion if available.
        if (server.pluginManager.getPlugin("PlaceholderAPI") != null) {
            logger.info("Attached PlaceholderAPI extension")
            PapiExpansion().register()
        } else {
            logger.info("PlaceholderAPI is not detected - skipping extension attachment")
        }
    }

    override fun onDisable() {
        logger.info("yapp yapp!! (see you soon <3)")
    }

    companion object {
        lateinit var instance: StickyChatBukkit
    }
}
