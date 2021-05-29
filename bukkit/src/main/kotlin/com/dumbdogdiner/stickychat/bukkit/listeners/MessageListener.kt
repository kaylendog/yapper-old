package com.dumbdogdiner.stickychat.bukkit.listeners

import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class MessageListener : WithPlugin, Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onAsyncPlayerChatEvent(ev: AsyncPlayerChatEvent) {
        // don't do this if another plugin cancelled this event already
        if (ev.isCancelled) { return }

        ev.isCancelled = true

        // send staff chat if they have it enabled.
        if (this.plugin.getStaffChatService(ev.player).hasStaffChatEnabled()) {
            this.plugin.getStaffChatService(ev.player).sendStaffChatMessage(ev.message)
            return
        }

        // staff chat prefix mode
        if (
            ev.message.startsWith(this.config.getString("chat.staff-chat-prefix", "+")!!) &&
            ev.player.hasPermission("stickychat.staff-chat")
        ) {
            this.plugin.getStaffChatService(ev.player).sendStaffChatMessage(ev.message.substring(1))
            return
        }

        this.plugin.getMessageService(ev.player).broadcast(ev.message)
    }
}
