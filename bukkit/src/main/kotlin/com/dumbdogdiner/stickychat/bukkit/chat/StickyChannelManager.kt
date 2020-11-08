package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.chat.Channel
import com.dumbdogdiner.stickychat.api.chat.ChannelManager
import java.util.UUID
import kotlin.collections.HashMap

class StickyChannelManager : ChannelManager {
    private val channels = HashMap<UUID, StickyChannel>()
    private val global = StickyChannel(UUID(0, 0), Channel.Type.GLOBAL, "global")

    init {
        channels[global.uniqueId] = global
    }

    override fun createChannel(type: Channel.Type, name: String): Channel {
        val channel = StickyChannel(UUID.randomUUID(), type, name)
        this.channels[channel.uniqueId] = channel
        return channel
    }

    override fun restoreChannel(uuid: UUID, type: Channel.Type, name: String): Channel {
        val channel = StickyChannel(uuid, type, name)
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
