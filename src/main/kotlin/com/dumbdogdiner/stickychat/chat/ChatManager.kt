package com.dumbdogdiner.stickychat.chat

import com.dumbdogdiner.stickychat.Base
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * Manages chat.
 */
class ChatManager : Base, Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {}
}