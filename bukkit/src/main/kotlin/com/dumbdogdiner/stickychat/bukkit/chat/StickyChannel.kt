package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.chat.Channel
import org.bukkit.entity.Player
import java.util.*

class StickyChannel(private val uniqueId: UUID, private var type: Channel.Type, private var name: String) : Channel {
    private val players = mutableListOf<Player>()

    override fun getUniqueId(): UUID {
        return this.uniqueId
    }

    override fun getType(): Channel.Type {
        return this.type
    }

    override fun setType(type: Channel.Type) {
        this.type = type
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
