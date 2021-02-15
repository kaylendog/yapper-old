package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.DataService
import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.channel.Channel
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

class StickyDataService private constructor(private val player: Player) : WithPlugin, DataService {
    private var priority = Priority.DEFAULT
    private var muted = false
    private var blockedPlayers = mutableListOf<OfflinePlayer>()
    private var channel = this.plugin.channelManager.globalChannel

    private var staffChatEnabled = false
    private var signSpyEnabled = false

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
        this.priority = priority
    }

    override fun getMuted(): Boolean {
        return this.muted
    }

    override fun getBlockedPlayers(): MutableList<OfflinePlayer> {
        return this.blockedPlayers
    }

    override fun getBlocked(player: Player): Boolean {
        return this.blockedPlayers.find { it.uniqueId == player.uniqueId } != null
    }

    override fun getSignSpyEnabled(): Boolean {
        return this.signSpyEnabled
    }

    override fun setSignSpyEnabled(enabled: Boolean) {
        this.signSpyEnabled = enabled
    }

    override fun getStaffChatEnabled(): Boolean {
        return this.staffChatEnabled
    }

    override fun setStaffChatEnabled(enabled: Boolean) {
        this.staffChatEnabled = enabled
    }

    override fun getChannel(): Channel {
        return this.channel
    }

    override fun setChannel(channel: Channel) {
        this.channel = channel
    }

    override fun save(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setBlocked(player: Player, blocked: Boolean) {
        if (this.blockedPlayers.contains(Bukkit.getOfflinePlayer(player.uniqueId))) {
            return
        }
        this.blockedPlayers.add(Bukkit.getOfflinePlayer(player.uniqueId))
    }

    override fun setMuted(muted: Boolean) {
        this.muted = muted
    }
}
