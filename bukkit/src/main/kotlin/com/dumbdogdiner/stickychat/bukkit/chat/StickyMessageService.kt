package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.chat.MessageService
import com.dumbdogdiner.stickychat.api.result.MessageResult
import com.dumbdogdiner.stickychat.api.result.MuteReason
import org.bukkit.entity.Player

class StickyMessageService private constructor(private val player: Player) : MessageService {
    companion object {
        private val services = HashMap<Player, StickyMessageService>()

        /**
         * Get the message service for the target player.
         */
        fun get(player: Player): StickyMessageService {
            if (services.containsKey(player)) {
                return services[player]!!
            }
            val service = StickyMessageService(player)
            services[player] = service
            return service
        }
    }

    override fun getPlayer(): Player {
        return player
    }

    override fun broadcast(message: String): MessageResult {
        if (this.dataService.muted) {
            return MessageResult.FAIL_MUTED
        }

        val recipients = MessageService.getRecipients(this.getPlayer(), Priority.DEFAULT)
        recipients.forEach { it.spigot().sendMessage(this.formatter.formatMessage(message)) }
        return MessageResult.OK
    }

    override fun mute(): Boolean {
        if (this.dataService.muted) {
            return false
        }
        this.dataService.muted = true
        return true
    }

    override fun mute(reason: MuteReason): Boolean {
        TODO("Not yet implemented")
    }

    override fun unmute(): Boolean {
        if (!this.dataService.muted) {
            return false
        }
        this.dataService.muted = false
        return true
    }
}
