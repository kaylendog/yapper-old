package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.Priority
<<<<<<< HEAD
import com.dumbdogdiner.stickychat.api.chat.StaffChatService
import com.dumbdogdiner.stickychat.api.util.SoundUtil
=======
import com.dumbdogdiner.stickychat.api.player.StaffChatManager
>>>>>>> c993afb... v4 :sparkles: api rewrite
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class StickyStaffChatService private constructor(private val player: Player) : WithPlugin, StaffChatManager {
    companion object : WithPlugin {
        private val staffChatServices = HashMap<Player, StaffChatManager>()

        /**
         * Get the data service for the target player.
         */
        fun get(player: Player): StaffChatManager {
            if (staffChatServices.containsKey(player)) {
                return staffChatServices[player]!!
            }
            val staffChatService = StickyStaffChatService(player)
            staffChatServices[player] = staffChatService
            return staffChatService
        }

        /**
         * Get appropriate recipients of a staff chat message.
         */
        fun getRecipients(): List<Player> {
            return Bukkit.getOnlinePlayers().filter {
                it.hasPermission("stickychat.staffchat") && !this.plugin.getDataService(it).priority.isGreaterThanOrEqualTo(Priority.IMPORTANT)
            }
        }
    }

    override fun getPlayer(): Player {
        return player
    }

    override fun enableStaffChat(): Boolean {
        if (this.dataService.staffChatEnabled) {
            return false
        }
        this.dataService.staffChatEnabled = true
        return true
    }

    override fun disableStaffChat(): Boolean {
        if (!this.dataService.staffChatEnabled) {
            return false
        }
        this.dataService.staffChatEnabled = false
        return true
    }

    override fun sendStaffChatMessage(message: String): Boolean {
//        if (!this.hasStaffChatEnabled()) {
//            return false
//        }
        // send to all recipients
        getRecipients().forEach {
            it.spigot().sendMessage(this.formatter.formatStaffChatMessage(message))
            SoundUtil.sendQuiet(it)
        }
        this.logger.info("[SC] ${this.player.name} $message")
        return true
    }
}
