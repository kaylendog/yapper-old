package com.dumbdogdiner.stickychat.bukkit.listeners

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

/**
 * Listens for player deaths and calls the DeathMessage API.
 */
class DeathListener : WithPlugin, Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val cause = e.entity.lastDamageCause
        if (cause == null) {
            this.logger.warning("Player ${e.entity.name} (${e.entity.uniqueId}) died without having a damage cause - cannot send death message")
            return
        }
        val message = StickyChat.getService().deathMessageService.getRandomOfType(cause.cause)
        e.deathMessage = message
    }
}
