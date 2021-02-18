package com.dumbdogdiner.stickychat.bukkit.channel

import com.dumbdogdiner.stickychat.api.channel.Channel
import com.dumbdogdiner.stickychat.api.channel.ChannelManager
import com.dumbdogdiner.stickychat.bukkit.messaging.StickyMessageService
import java.util.UUID
import kotlin.collections.HashMap

class SkChannelManager : ChannelManager {
    private val channels = HashMap<UUID, skChannel>()
    private val global = skChannel(UUID(0, 0), Channel.Type.GLOBAL, "global")

    init {
        channels[global.uniqueId] = global
    }

    override fun createChannel(type: Channel.Type, name: String): Channel {
        val channel = skChannel(UUID.randomUUID(), type, name)
        this.channels[channel.uniqueId] = channel
        return channel
    }

    override fun restoreChannel(uuid: UUID, type: Channel.Type, name: String): Channel {
        val channel = skChannel(uuid, type, name)
        this.channels[channel.uniqueId] = channel
        return channel
    }

    override fun getGlobalChannel(): Channel {
        return this.global
    }

    override fun removeChannel(id: UUID?): Boolean {
        if (id == global.uniqueId) {
            return false
        }

        val channelToRemove = channels[id] ?: return false

        // move all players to global channel.
        channelToRemove.players.forEach { StickyMessageService.get(it).moveChannel(this.global) }
        channels.remove(id)
        return true
    }

    override fun getChannel(id: UUID): Channel? {
        return channels[id]
    }

    override fun getChannels(): List<Channel> {
        return this.channels.values.toList()
    }
}
