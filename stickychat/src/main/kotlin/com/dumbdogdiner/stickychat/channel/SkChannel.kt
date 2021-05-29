/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.channel

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.channel.Channel
import com.dumbdogdiner.stickychat.api.channel.ChannelType
import com.dumbdogdiner.stickychat.api.channel.MessageResult
import com.dumbdogdiner.stickychat.ext.hasBlocked
import com.dumbdogdiner.stickychat.ext.priority
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.entity.Player

class SkChannel(private val type: ChannelType, private var name: String) : Channel {
    private val players = mutableListOf<Player>()
    private val mutedPlayers = mutableListOf<Player>()

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
        // check if the player is already in this channel
        if (this.players.contains(player)) {
            return false
        }
        // add the player to the channel
        this.players.add(player)
        return true
    }

    override fun removePlayer(player: Player): Boolean {
        return this.players.remove(player)
    }

    override fun send(from: Player, message: BaseComponent): MessageResult {
        // test if muted
        if (this.isMuted(from)) {
            return MessageResult.FAIL_MUTED
        }
        // fetch recipients
        val recipients = this.players.filter {
            it.priority.isGreaterThan(Priority.DEFAULT) &&
                !it.hasBlocked(from)
        }
        // send message
        recipients.forEach { it.sendMessage(message) }
        return MessageResult.OK
    }

    override fun getMutedPlayers(): List<Player> {
        return this.mutedPlayers
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
