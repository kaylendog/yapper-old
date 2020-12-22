package com.dumbdogdiner.stickychat.bukkit.listeners

import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class MessageListener : WithPlugin, Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onAsyncPlayerChatEvent(ev: AsyncPlayerChatEvent) {
        this.plugin.getMessageService(ev.player).broadcast(ev.message)
        ev.isCancelled = true
    }
}
