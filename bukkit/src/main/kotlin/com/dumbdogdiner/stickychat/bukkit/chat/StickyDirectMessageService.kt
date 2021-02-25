package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.chat.DirectMessageService
import com.dumbdogdiner.stickychat.api.result.DirectMessageResult
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.entity.Player

class StickyDirectMessageService private constructor(private val player: Player) : WithPlugin, DirectMessageService {
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
        if (target == this.player && !this.config.getBoolean("debug.enable-self-message", false)) {
            this.integration.sendSystemError(this.player, "You cannot message yourself, dork!")
            SoundUtil.sendError(this.player)
            return DirectMessageResult.FAIL_SELF
        }

        val result = StickyChat.getService().getDirectMessageService(target).receive(this.player, message)
        if (result != DirectMessageResult.OK) {
            this.integration.sendSystemError(this.player, "Could not send direct message!")
            return result
        }
        this.player.spigot().sendMessage(this.formatter.formatOutgoingDM(target, message))
        this.lastPlayer = target
        this.lastDirectMessageService?.setLastPlayer(this.player)

        if (result == DirectMessageResult.OK) {
            SoundUtil.sendQuiet(this.getPlayer())
        }

        return result
    }

    override fun getLast(): Player? {
        return this.lastPlayer
    }

    override fun setLastPlayer(player: Player) {
        this.lastPlayer = player
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
        this.player.spigot().sendMessage(this.formatter.formatIncomingDM(from, message))
        SoundUtil.sendQuiet(this.getPlayer())
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

    override fun sendSystemMessage(message: BaseComponent): DirectMessageResult {
        player.spigot().sendMessage(message)
        return DirectMessageResult.OK
    }
}
