package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.chat.StaffChatService
import org.bukkit.entity.Player

class StickyStaffChatService private constructor(private val player: Player) : StaffChatService {
    companion object {
        private val staffChatServices = HashMap<Player, StaffChatService>()

        /**
         * Get the data service for the target player.
         */
        fun get(player: Player): StaffChatService {
            if (staffChatServices.containsKey(player)) {
                return staffChatServices[player]!!
            }
            val staffChatService = StickyStaffChatService(player)
            staffChatServices[player] = staffChatService
            return staffChatService
        }
    }

    private val staffChatEnabled = false

    override fun getPlayer(): Player {
        return player
    }

    override fun enableStaffChat(): Boolean {
        if (this.staffChatEnabled) {
            return false
        }
        this.dataService.setStaffChatEnabled(true)
        return true
    }

    override fun disableStaffChat(): Boolean {
        if (!this.staffChatEnabled) {
            return false
        }
        this.dataService.setStaffChatEnabled(false)
        return true
    }

    override fun sendStaffChatMessage(message: String): Boolean {
        if (!this.hasStaffChatEnabled()) {
            return false
        }

        if (this.dataService.priority.isGreaterThan(Priority.IMPORTANT)) {
            return false
        }

        this.getPlayer().spigot().sendMessage(this.formatter.formatStaffChatMessage(message))
        return true
    }
}
