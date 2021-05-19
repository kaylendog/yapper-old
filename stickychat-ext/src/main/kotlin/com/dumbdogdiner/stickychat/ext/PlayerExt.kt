package com.dumbdogdiner.stickychat.ext

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.messaging.Formatter
import com.dumbdogdiner.stickychat.api.util.NotificationType
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

val chat = StickyChat.getService()

/**
 * Send a regular string message to a player.
 */
fun Player.sendMessage(message: String) {
	val component = TextComponent()
	component.text = message
	return this.sendMessage(component)
}

/**
 * Send a text component to a player without having to call Player.spigot()
 */
fun Player.sendMessage(component: TextComponent) {
	val formatted = Formatter.formatHexCodes(component)
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
 * The nickname of this player.
 */
var Player.nickname
	get() = chat.nicknameManager.getNickname(this)
	set(value: String?) = run {
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
