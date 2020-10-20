package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.chat.StaffChatService
import org.bukkit.entity.Player

class StickyStaffChatService private constructor(private val player: Player) : StaffChatService {
    private val staffChatEnabled = false

    override fun getPlayer(): Player {
        return player;
    }

    override fun enableStaffChat(): Boolean {
        if (this.staffChatEnabled) {
            return false;
        }
        this.dataService.setStaffChatEnabled(true);
        return true;
    }

    override fun disableStaffChat(): Boolean {
        if (!this.staffChatEnabled) {
            return false;
        }
        this.dataService.setStaffChatEnabled(false);
        return true;
    }

    override fun sendStaffChatMessage(message: String): Boolean {
        if (!this.hasStaffChatEnabled()) {
            return false;
        }


    }
}
