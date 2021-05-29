/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.ext

import com.dumbdogdiner.stickychat.Formatter
import com.dumbdogdiner.stickychat.api.channel.Channel
import com.dumbdogdiner.stickychat.api.util.NotificationType
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull

/**
 * Send a regular string message to a player.
 */
fun Player.sendMessage(message: String) {
    return this.sendMessage(message.asComponent())
}

/**
 * Send a text component to a player without having to call Player.spigot()
 */
fun Player.sendMessage(component: TextComponent) {
    val formatted = com.dumbdogdiner.stickychat.Formatter.formatHexCodes(component)
    chat.directMessageManager.sendRawMessage(this, formatted)
}

/**
 * Send a message to this player with a notification.
 */
fun Player.sendMessage(notificationType: NotificationType, component: TextComponent) {
    SoundUtil.send(this, notificationType)
    this.sendMessage(component)
}

/**
 * Send a message to this player with a notification.
 */
fun Player.sendMessage(notificationType: NotificationType, message: String) {
    this.sendMessage(notificationType, TextComponent(message))
}

/**
 * Send an error message to this player.
 */
fun Player.sendError(textComponent: TextComponent) {
    this.sendMessage(NotificationType.ERROR, textComponent)
}

/**
 * Send an error message to this player.
 */
fun Player.sendError(message: String) {
    this.sendError(TextComponent(message))
}

/**
 * The nickname of this player.
 */
var Player.nickname
    get() = chat.nicknameManager.getNickname(this)
    set(value) = run {
        if (value == null) {
            chat.nicknameManager.clearNickname(this)
        } else {
            chat.nicknameManager.setNickname(this, value)
        }
    }

/**
 * The display name of this player.
 */
var Player.displayName
    get() = chat.nicknameManager.getDisplayname(this)
    set(value) = run {
        chat.nicknameManager.setNickname(this, value)
    }

/**
 * The priority of this player.
 */
val Player.priority
    get() = chat.priorityManager.getPriority(this)

/**
 * The current channels this player is in.
 */
val Player.currentChannels
    get() = chat.channelManager.getPlayerChannels(this)

/**
 * Join this player to the target channel.
 * @param channel The channel to join
 */
fun Player.joinChannel(channel: Channel) {
    channel.addPlayer(this)
}

/**
 * Leave the target channel.
 * @param channel The target channel
 */
fun Player.leaveChannel(channel: Channel) {
    channel.removePlayer(this)
}

/**
 * Block the target player.
 * @param player The target player
 */
fun Player.block(player: Player) {
    chat.playerBlockManager.block(this, player)
}

/**
 * Unblock the target player.
 * @param player The target player
 */
fun Player.unblock(player: Player) {
    chat.playerBlockManager.unblock(this, player)
}

/**
 * Test if this player has the target player blocked.
 * @param player The target player
 */
fun Player.hasBlocked(player: Player): @NotNull Boolean {
    return chat.playerBlockManager.hasBlocked(this, player)
}
