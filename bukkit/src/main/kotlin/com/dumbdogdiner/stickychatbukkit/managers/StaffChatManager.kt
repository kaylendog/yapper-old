package com.dumbdogdiner.stickychatbukkit.managers

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils
import com.dumbdogdiner.stickychatbukkit.utils.Priority
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Manages staff chat.
 */
class StaffChatManager : Base {
    private val enabledPlayers = HashMap<String, Player>()

    /**
     * Return whether the target player has staff chat enabled.
     */
    fun getStaffChatEnabled(player: Player): Boolean {
        return enabledPlayers.containsValue(player)
    }

    /**
     * Enable staff chat for the target player.
     */
    fun enableStaffChat(player: Player) {
        enabledPlayers[player.uniqueId.toString()] = player
    }

    /**
     * Disable staff chat for the target player.
     */
    fun disableStaffChat(player: Player) {
        enabledPlayers.remove(player.uniqueId.toString())
    }

    /**
     * Check if the parsed message is destined for staff chat.
     */
    fun isStaffChatOverride(player: Player, content: String): Boolean {
        if (!player.hasPermission("stickychat.staffchat.view")) {
            return false
        }

        if (!content.startsWith(config.getString("chat.staff-chat-prefix", "+")!!)) {
            return false
        }

        return true
    }

    /**
     * Send a staff chat message.
     */
    fun sendStaffChatMessage(from: Player, content: String) {
        val staff = Bukkit.getOnlinePlayers().filter { it.hasPermission("stickychat.staffchat.view") }
        val message = FormatUtils.formatStaffChatMessage(from, content)

        staff.forEach {
            chatManager.sendMessage(it, Priority.IMPORTANT, message)
        }
    }
}
