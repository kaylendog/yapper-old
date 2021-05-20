/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.channel

import com.dumbdogdiner.stickychat.api.channel.Channel
import com.dumbdogdiner.stickychat.api.channel.ChannelManager
import com.dumbdogdiner.stickychat.api.channel.ChannelType
import java.util.UUID
import kotlin.collections.HashMap
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player

class SkChannelManager : ChannelManager {
    private val channels = HashMap<UUID, SkChannel>()
    private val global = SkChannel(UUID(0, 0), ChannelType.GLOBAL, "global")

    init {
        channels[global.getUniqueId()] = global
    }

    override fun createChannel(type: ChannelType, name: String): Channel {
        val channel = SkChannel(UUID.randomUUID(), type, name)
        this.channels[channel.getUniqueId()] = channel
        return channel
    }

    override fun restoreChannel(uuid: UUID, type: ChannelType, name: String): Channel {
        val channel = SkChannel(uuid, type, name)
        this.channels[channel.getUniqueId()] = channel
        return channel
    }

    override fun deserialize(key: String, section: ConfigurationSection): Channel {
        TODO("Not yet implemented")
    }

    override fun getGlobalChannel(): Channel {
        return this.global
    }

    override fun removeChannel(id: UUID?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getChannel(id: UUID): Channel? {
        return channels[id]
    }

    override fun getPlayerChannel(player: Player): Channel {
        TODO("Not yet implemented")
    }

    override fun getChannels(): List<Channel> {
        return this.channels.values.toList()
    }
}
