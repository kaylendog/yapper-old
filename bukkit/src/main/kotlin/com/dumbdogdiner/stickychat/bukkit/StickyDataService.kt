package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.DataService
import com.dumbdogdiner.stickychat.api.Priority
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class StickyDataService private constructor(private val player: Player) : DataService {
    private var priority = Priority.DEFAULT

    companion object {
        private val dataServices = HashMap<Player, StickyDataService>()

        /**
         * Get the data service for the target player.
         */
        fun get(player: Player): StickyDataService {
            if (dataServices.containsKey(player)) {
                return dataServices[player]!!
            }
            val dataService = StickyDataService(player)
            dataServices[player] = dataService
            return dataService
        }
    }

    override fun getPlayer(): Player {
        return this.player
    }

    override fun getPriority(): Priority {
        return this.priority
    }

    override fun setPriority(priority: Priority) {
        TODO("Not yet implemented")
    }

    override fun getMuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBlockedPlayers(): MutableList<OfflinePlayer> {
        TODO("Not yet implemented")
    }

    override fun getBlocked(player: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSignSpyEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun save(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSignSpyEnabled(enabled: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun setBlocked(player: Player, blocked: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setMuted(muted: Boolean) {
        TODO("Not yet implemented")
    }
}