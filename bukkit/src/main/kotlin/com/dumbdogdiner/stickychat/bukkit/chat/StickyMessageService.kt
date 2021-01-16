package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.chat.Channel
import com.dumbdogdiner.stickychat.api.chat.MessageService
import com.dumbdogdiner.stickychat.api.result.MessageResult
import com.dumbdogdiner.stickychat.api.result.MuteReason
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import com.dumbdogdiner.stickychat.bukkit.redis.PacketBuilder
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class StickyMessageService private constructor(private val player: Player) : WithPlugin, MessageService {
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

    override fun getChannel(): Channel {
        return this.dataService.channel
    }

    override fun moveChannel(channel: Channel): Boolean {
        if (this.channel == channel) {
            this.integration.sendSystemError(this.player, "You are already in channel ${channel.name}!")
            return false
        }

        this.dataService.channel = channel
        this.integration.sendSystemMessage(this.player, "Changed channels to ${channel.name}")
        return true
    }

    override fun broadcast(message: String): MessageResult {
        if (this.dataService.muted) {
            return MessageResult.FAIL_MUTED
        }

        val recipients = MessageService.getRecipients(this.getPlayer(), Priority.DEFAULT)
        val formattedMessage = this.formatter.formatMessage(message)

        recipients.forEach { it.spigot().sendMessage(formattedMessage) }
        Bukkit.getConsoleSender().sendMessage(formattedMessage)
        this.plugin.redisMessenger.sendRaw("stickychat", PacketBuilder(PacketBuilder.Type.MESSAGE).sender(this.player.name).content(message).build())

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
