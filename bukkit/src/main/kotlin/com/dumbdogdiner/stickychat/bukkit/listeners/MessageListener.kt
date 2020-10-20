package com.dumbdogdiner.stickychat.bukkit.listeners

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.StickyChat
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class MessageListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onAsyncPlayerChatEvent(ev: AsyncPlayerChatEvent) {
        val message = StickyChat.getService().getFormatter(ev.player).formatMessage(ev.message)
        ev.recipients.removeIf {
            StickyChat.getService().getDataService(it).getBlocked(ev.player) or
            StickyChat.getService().getDataService(it).priority.isGreaterThan(Priority.DEFAULT)
        }

        ev.isCancelled = true
        ev.recipients.forEach { it.spigot().sendMessage(message) }
    }
}
