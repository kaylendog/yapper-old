package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.chat.NicknameService
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import com.dumbdogdiner.stickychat.bukkit.models.Nicknames
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class StickyNicknameService(private val player: Player) : WithPlugin, NicknameService {
    private var cachedNickname: String? = null

    companion object {
        private val nicknameServices = HashMap<Player, NicknameService>()

        /**
         * Get the data service for the target player.
         */
        fun get(player: Player): NicknameService {
            if (nicknameServices.containsKey(player)) {
                return nicknameServices[player]!!
            }
            val nicknameService = StickyNicknameService(player)
            nicknameServices[player] = nicknameService
            return nicknameService
        }
    }

    override fun getPlayer(): Player {
        return this.player
    }

    override fun getNickname(): String? {
        return cachedNickname
    }

    override fun setNickname(newNickname: String) {
        cachedNickname = newNickname

        val uniqueId = this.player.uniqueId.toString()

        if (this.plugin.sqlEnabled) {
            transaction {
                Nicknames.update({ Nicknames.player eq player.uniqueId.toString() and Nicknames.active }) {
                    it[active] = false
                }
                Nicknames.insert {
                    it[nickname] = newNickname
                    it[player] = uniqueId
                    it[active] = true
                }
            }
        }

        this.logger.info("Updated nickname for player '${this.player.name}' (${this.player.uniqueId})")
    }

    override fun removeNickname() {
        cachedNickname = null
        if (this.plugin.sqlEnabled) {
            transaction {
                Nicknames.update({ Nicknames.player eq player.uniqueId.toString() and Nicknames.active }) {
                    it[active] = false
                }
            }
        }
    }

    override fun hasNickname(): Boolean {
        return cachedNickname != null
    }

    override fun loadNickname(): Boolean {
        if (!this.plugin.sqlEnabled) {
            logger.warning("Could not look up nickname for  '${this.player.name}' (${this.player.uniqueId}) - SQL is not enabled")
            return false
        }

        logger.info("Looking up settings and nickname for '${this.player.name}' (${this.player.uniqueId})...")

        val nickname = transaction {
            return@transaction Nicknames.select { Nicknames.player eq player.uniqueId.toString() and Nicknames.active }.firstOrNull()
        }
            ?: return true

        this.cachedNickname = nickname[Nicknames.nickname]
        return true
    }
}
