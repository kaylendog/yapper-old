package com.dumbdogdiner.stickychat.bukkit.player

import com.dumbdogdiner.stickychat.api.player.PlayerBlockManager
import com.dumbdogdiner.stickychat.bukkit.models.Blocks
import java.util.HashMap
import java.util.UUID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/**
 * Handles block data.
 */
class SkPlayerBlockManager : PlayerBlockManager {
    private val cachedBlocks = mutableMapOf<UUID, MutableSet<UUID>>()

    /**
     * Load blocks for the target player uuid.
     */
    override fun loadBlocks(player: UUID): Boolean {
        // create the cache entry
        val blocks = cachedBlocks.getOrPut(player, { mutableSetOf() })
        // query the database for blocks
        transaction {
            Blocks.select {
                Blocks.player eq player.toString() and Blocks.active
            }.forEach {
                // add each block to the set
                blocks.add(UUID.fromString(it[Blocks.target]))
            }
        }
        return true
    }

    /**
     * Adds the block to the cache and the database.
     */
    override fun block(player: UUID, target: UUID): Boolean {
        // update the database - do this first in case it errors
        transaction {
            Blocks.insert {
                it[Blocks.player] = player.toString()
                it[Blocks.target] = target.toString()
                it[active] = true
                it[createdAt] = System.currentTimeMillis()
            }
        }
        // add the player to cache - creates the set if it does not exist
        cachedBlocks.getOrPut(player, { mutableSetOf() }).add(target)
        return true
    }

    /**
     * Removes a block from the cache and the database.
     */
    override fun unblock(player: UUID, target: UUID): Boolean {
        // update the database - do this first in case it errors
        transaction {
            Blocks.update({ Blocks.player eq player.toString() }) {
                it[active] = false
                it[deactivatedAt] = System.currentTimeMillis()
            }
        }
        // remove the player from the cache
        cachedBlocks.getOrPut(player, { mutableSetOf() }).remove(target)
        return true
    }

    /**
     * Tests if the player has blocked the other.
     */
    override fun hasBlocked(player: UUID, target: UUID): Boolean {
        // check if the cache initially has the player
        if (cachedBlocks.containsKey(player)) {
            return cachedBlocks[player]!!.contains(target)
        }
        // their data is not loaded - must load it!
        this.loadBlocks(player)
        return cachedBlocks.getOrPut(player, { mutableSetOf() }).contains(target)
    }

    override fun getAllBlocks(): HashMap<UUID, Set<UUID>> {
        return HashMap(this.cachedBlocks.mapValues { it.value.toSet() })
    }
}
