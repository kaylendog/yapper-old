package com.dumbdogdiner.stickychat.bukkit.player

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.player.PriorityManager
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import com.dumbdogdiner.stickychat.bukkit.models.Priorities
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/**
 * Manages player priorities.
 */
class SkPriorityManager : WithPlugin, PriorityManager {
    /**
     * In-memory nickname cache.
     */
    private val cachedPriorities = mutableMapOf<Player, Priority>()

    override fun loadPriority(player: Player): Boolean {
        return transaction {
            // select transaction on database, return false if none found
            val priority = Priorities.select {
                Priorities.player eq player.uniqueId.toString() and Priorities.active
            }.firstOrNull() ?: return@transaction false

            // load into cache, inform action was successful
            cachedPriorities[player] = Priority.values()[priority[Priorities.priority]]
            true
        }
    }

    /**
     * Test if the priority cache has the target player.
     */
    private fun hasCachedPriority(player: Player): Boolean {
        return this.cachedPriorities.contains(player)
    }

    /**
     * Gets the player priority if it exists - if not, return the default.
     */
    override fun getPriority(player: Player): Priority {
        // if not loaded, load
        if (!this.hasCachedPriority(player)) {
            this.loadPriority(player)
        }
        // return the default as a fallback
        return this.cachedPriorities.getOrDefault(player, Priority.DEFAULT)
    }

    /**
     * Sets the player's priority, updating the database and the cache.
     */
    override fun setPriority(target: Player, newPriority: Priority): Boolean {
        this.cachedPriorities[target] = newPriority
        return transaction {
            // deactivate previous nicknames
            Priorities.update({ Priorities.player eq target.uniqueId.toString() and Priorities.active }) {
                it[active] = false
            }
            // insert new nickname into database
            Priorities.insert {
                it[player] = target.uniqueId.toString()
                it[priority] = newPriority.ordinal
                it[active] = true
            }
            true
        }
    }
}
