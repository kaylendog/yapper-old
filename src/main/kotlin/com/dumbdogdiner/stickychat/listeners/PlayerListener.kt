package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.Base
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener : Base, Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
    }

    @EventHandler
    fun handleChatEvent(e: AsyncPlayerChatEvent) {
        val format = storageManager.getUserFormat(e.player)
    }
}
