/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.channel

import com.dumbdogdiner.stickychat.api.channel.Channel
import com.dumbdogdiner.stickychat.api.channel.ChannelType
import java.util.UUID
import org.bukkit.entity.Player

class SkChannel(private val uniqueId: UUID, private var type: ChannelType, private var name: String) : Channel {
    private val players = mutableListOf<Player>()

    override fun getUniqueId(): UUID {
        return this.uniqueId
    }

    override fun getType(): ChannelType {
        return this.type
    }

    override fun getName(): String {
        return this.name
    }

    override fun setName(name: String) {
        this.name = name
    }

    override fun getPlayers(): List<Player> {
        return this.players
    }

    override fun addPlayer(player: Player): Boolean {
        if (this.players.contains(player)) {
            return false
        }
        this.players.add(player)
        return true
    }

    override fun removePlayer(player: Player): Boolean {
        if (!this.players.contains(player)) {
            return false
        }
        this.players.remove(player)
        return true
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
