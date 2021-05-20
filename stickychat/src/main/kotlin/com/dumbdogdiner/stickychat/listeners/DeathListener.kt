/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.api.Formatter
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.util.Placeholders
import com.dumbdogdiner.stickychat.api.util.StringModifier
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent

/**
 * Listens for player deaths and calls the DeathMessage API.
 */
class DeathListener : WithPlugin, Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerDeath(e: PlayerDeathEvent) {
        if (e.isCancelled) {
            return
        }

        val cause = e.entity.lastDamageCause
        if (cause == null) {
            this.logger.warning("Player ${e.entity.name} (${e.entity.uniqueId}) died without having a damage cause - cannot send death message")
            return
        }

        // fetch attacker
        var attacker: Player? = null
        if (e.entity.lastDamageCause is EntityDamageByEntityEvent && (e.entity.lastDamageCause as EntityDamageByEntityEvent).damager is Player) {
            attacker = (e.entity.lastDamageCause as EntityDamageByEntityEvent).damager as Player
        }

        val message = StickyChat.getService().deathMessageService.getRandomOfType(cause.cause)
        val interp = Formatter.formatHexCodes(
                StringModifier(this.config.getString("chat.death-message-format", "%message%")!!)
                    .replace("%message%", message)
                    .replace("%player_name%", e.entity.name)
                    .replace("%attacker%", attacker?.name ?: "")
                    .apply { Placeholders.setPlaceholdersSafe(e.entity, it) }
                    .get()
        )
        // broadcast and disable default
        e.deathMessage = null
        Bukkit.getOnlinePlayers().forEach { it.spigot().sendMessage(interp) }
    }
}
