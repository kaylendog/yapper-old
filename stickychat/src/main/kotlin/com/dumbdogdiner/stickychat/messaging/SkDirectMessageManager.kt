/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.messaging

import com.dumbdogdiner.stickychat.api.event.DirectMessageEvent
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageManager
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageResult
import com.dumbdogdiner.stickychat.bukkit.SkFormatter
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SkDirectMessageManager : WithPlugin, DirectMessageManager {
    // internal caches
    private val lastPlayers = mutableMapOf<Player, String>()
    private val disabledPlayers = mutableSetOf<Player>()

    override fun sendMessage(from: Player, to: String, message: String): DirectMessageResult {
        // check for no player, send external message
        val player = Bukkit.getPlayerExact(to) ?: return this.sendExternalMessage(from, to, message)
        // send internal message
        return this.sendInternalMessage(from, player, message)
    }

    override fun sendExternalMessage(from: Player, to: String, message: String): DirectMessageResult {
        TODO("Not yet implemented")
    }

    /**
     * Send a message to the target player.
     */
    override fun sendInternalMessage(from: Player, to: Player, message: String): DirectMessageResult {
        // check for player blocks
        if (this.plugin.playerBlockManager.hasBlocked(to.uniqueId, from.uniqueId)) {
            return DirectMessageResult.FAIL_BLOCK
        }
        // check for disabled
        if (this.disabledPlayers.contains(from)) {
            return DirectMessageResult.FAIL_DISABLED
        }
        // call event
        val ev = DirectMessageEvent(from, to, message)
        this.callBukkitEvent(ev)
        if (ev.isCancelled) {
            return DirectMessageResult.withErrorMessage(ev.cancelReason)
        }
        // send formatted message
        to.spigot().sendMessage(SkFormatter.formatIncomingDM(to, from, message))
        from.spigot().sendMessage(SkFormatter.formatOutgoingDM(from, to, message))
        return DirectMessageResult.OK
    }

    override fun sendRawMessage(player: Player, message: TextComponent): DirectMessageResult {
        TODO("Not yet implemented")
    }

    /**
     * Gets the name of the last player the target player messaged.
     */
    override fun getLast(player: Player): String? {
        return lastPlayers[player]
    }

    /**
     * Reply to the target player - attempts to lookup a player name in the cache,
     * and send them a message.
     */
    override fun reply(player: Player, message: String): DirectMessageResult {
        if (!this.hasLastPlayer(player)) {
            return DirectMessageResult.FAIL_DISABLED
        }
        return this.sendMessage(player, this.getLast(player)!!, message)
    }

    override fun getDisabledPlayers(): Collection<Player> {
        return this.disabledPlayers
    }

    override fun hasDirectMessagesDisabled(player: Player): Boolean {
        return this.disabledPlayers.contains(player)
    }

    override fun enableDirectMessages(player: Player) {
        this.disabledPlayers.add(player)
    }

    override fun disableDirectMessages(player: Player) {
        this.disabledPlayers.remove(player)
    }
}
