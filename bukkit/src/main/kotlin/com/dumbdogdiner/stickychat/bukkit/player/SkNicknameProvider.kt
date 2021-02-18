package com.dumbdogdiner.stickychat.bukkit.player

import com.dumbdogdiner.stickychat.api.player.NicknameProvider
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import com.dumbdogdiner.stickychat.bukkit.models.Nicknames
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class SkNicknameProvider : WithPlugin, NicknameProvider {
    /**
     * In-memory nickname cache.
     */
    private val cachedNicknames = mutableMapOf<Player, String?>()

    /**
     * Loads nicknames from PostgreSQL.
     */
    override fun loadNickname(player: Player): Boolean {
        return transaction {
            // select transaction on database, return false if none found
            val nickname = Nicknames.select {
                Nicknames.player eq player.uniqueId.toString() and Nicknames.active
            }.firstOrNull() ?: return@transaction false

            // load into cache, inform action was successful
            cachedNicknames[player] = nickname[Nicknames.nickname]
            true
        }
    }

    /**
     * Tests if a player has a nickname - first checks the cache, then loads them from PostgreSQL.
     */
    override fun hasNickname(player: Player): Boolean {
        if (this.cachedNicknames.contains(player) && this.cachedNicknames[player] != null) {
            return true
        }
        // attempt to test if their nickname exists by loading it
        return this.loadNickname(player)
    }

    /**
     * Gets the player nickname by testing if it exists, and then returning the cached nickname.
     * Since `hasNickname` will attempt to load it if it doesn't exist, this will load it
     * into memory for the cache return.
     */
    override fun getNickname(player: Player): String? {
        if (!this.hasNickname(player)) {
            return null
        }
        return this.cachedNicknames[player]
    }

    /**
     * Sets the nickname for the target player. If the new nickname is null, it removes existing
     * nicknames from the cache and the database. Otherwise, it updates both to reflect
     * the new nickname.
     */
    override fun setNickname(target: Player, newNickname: String?): Boolean {
        this.cachedNicknames[target] = newNickname
        // remove from cache if null
        if (newNickname == null) {
            this.cachedNicknames.remove(target)
        }
        return transaction {
            // deactivate previous nicknames
            Nicknames.update({ Nicknames.player eq target.uniqueId.toString() and Nicknames.active }) {
                it[active] = false
            }
            // if new nickname is null, don't need to create new database entry
            if (newNickname == null) {
                return@transaction true
            }
            // insert new nickname into database
            Nicknames.insert {
                it[player] = target.uniqueId.toString()
                it[nickname] = newNickname
                it[active] = true
            }
            true
        }
    }

}
