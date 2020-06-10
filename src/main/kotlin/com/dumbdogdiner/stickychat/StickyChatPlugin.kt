package com.dumbdogdiner.stickychat

import com.dumbdogdiner.stickychat.signspy.SignSpyManager
import org.bukkit.plugin.java.JavaPlugin

@kr.entree.spigradle.Plugin
class StickyChatPlugin : JavaPlugin() {
    lateinit var signSpyManager: SignSpyManager

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        signSpyManager = SignSpyManager()
    }

    override fun onDisable() { }

    companion object {
        lateinit var instance: StickyChatPlugin
    }
}
