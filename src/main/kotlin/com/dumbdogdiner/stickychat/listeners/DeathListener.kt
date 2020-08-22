package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.files.DeathMessages
import com.dumbdogdiner.stickychat.utils.Priority
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
