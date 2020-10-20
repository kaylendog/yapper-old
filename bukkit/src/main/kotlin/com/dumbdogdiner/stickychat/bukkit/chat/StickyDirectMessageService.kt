package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.chat.DirectMessageService
import com.dumbdogdiner.stickychat.api.result.DirectMessageResult
import org.bukkit.entity.Player

class StickyDirectMessageService private constructor(private val player: Player) : DirectMessageService {
    companion object {
        private val services = HashMap<Player, StickyDirectMessageService>()

        /**
         * Get the direct message service for the target player.
         */
        fun get(player: Player): StickyDirectMessageService {
            if (services.containsKey(player)) {
                return services[player]!!
            }
            val service = StickyDirectMessageService(player)
            services[player] = service
            return service
        }
    }

    private var lastPlayer: Player? = null

    override fun getPlayer(): Player {
        return this.player
    }

    override fun sendTo(target: Player, message: String): DirectMessageResult {
        val result = StickyChat.getService().getDirectMessageService(target).receive(this.player, message)
        if (result != DirectMessageResult.OK) {
            return result
        }
        this.player.sendMessage(this.formatter.formatOutgoingDM(target, message))
        this.lastPlayer = target
        return result
    }

    override fun sendToLast(message: String): DirectMessageResult {
        if (this.lastPlayer == null) {
            return DirectMessageResult.FAIL_NONEXISTENT
        }
        return this.sendTo(this.lastPlayer!!, message)
    }

    override fun receive(from: Player, message: String): DirectMessageResult {
        if (this.isBlocked(from)) {
            return DirectMessageResult.FAIL_BLOCK
        }
        if (this.dataService.priority.isGreaterThan(Priority.DIRECT)) {
            return DirectMessageResult.FAIL_PRIORITY
        }
        this.player.sendMessage(this.formatter.formatIncomingDM(from, message))
        return DirectMessageResult.OK
    }

    override fun block(target: Player): Boolean {
        if (this.isBlocked(target)) {
            return false
        }
        this.dataService.setBlocked(target, true)
        return true
    }

    override fun unblock(target: Player): Boolean {
        if (!this.isBlocked(target)) {
            return false
        }
        this.dataService.setBlocked(target, false)
        return true
    }
}
