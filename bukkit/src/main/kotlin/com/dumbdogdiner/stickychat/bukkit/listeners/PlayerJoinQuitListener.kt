package com.dumbdogdiner.stickychat.bukkit.listeners

import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Listens for player joins and caches appropriate values. Forgets them when they leave.
 */
class PlayerJoinQuitListener : WithPlugin, Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        logger.info("Looking up settings and nickname for ${e.player.name} (${e.player.uniqueId})")
    }

    @EventHandler
    fun onPlayerLeave(e: PlayerQuitEvent) {
        logger.info("Saving settings for ${e.player} (${e.player.uniqueId})")
    }
}
