package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.Base
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

class SignListener : Base, Listener {
    @EventHandler
    fun onSignChanged(e: SignChangeEvent) {
        if (!config.getBoolean("sign-spy.enabled", true)) {
            return
        }
    }
}
