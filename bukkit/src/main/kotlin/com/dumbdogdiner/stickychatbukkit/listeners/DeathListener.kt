package com.dumbdogdiner.stickychatbukkit.listeners

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.files.DeathMessages
import com.dumbdogdiner.stickychatbukkit.utils.Priority
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent

/**
 * Listens for player deaths.
 */
class DeathListener : Base, Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val damageEvent = e.entity.lastDamageCause ?: return
        val message = TextComponent()

        var attacker = e.entity.killer as Entity?

        if (attacker == null && damageEvent is EntityDamageByEntityEvent) {
            attacker = damageEvent.damager
        }

        message.text = DeathMessages.getRandomFormatted(e.entity, damageEvent.cause, attacker)

        chatManager.sendMessageToAllPlayers(Priority.ALL, message)
        e.deathMessage = null
    }
}
