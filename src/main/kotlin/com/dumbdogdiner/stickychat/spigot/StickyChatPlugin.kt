package com.dumbdogdiner.stickychat.spigot

import com.dumbdogdiner.stickychat.Constants
import com.dumbdogdiner.stickychat.spigot.commands.TestCommand
import com.dumbdogdiner.stickychat.spigot.utils.ServerUtils
import org.bukkit.plugin.java.JavaPlugin

class StickyChatPlugin : JavaPlugin() {
    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        ServerUtils.log("Registering commands...")
        val testCommand = getCommand("test")!!
        testCommand.setExecutor(TestCommand())

        ServerUtils.log("Attatching events...")

        // Plugin messaging
        ServerUtils.log("Registering plugin messenger...")
        server.messenger.registerOutgoingPluginChannel(this, Constants.CHANNEL_NAME)

        ServerUtils.log("Done.")
    }

    override fun onDisable() { }

    companion object {
        lateinit var instance: StickyChatPlugin
    }
}
