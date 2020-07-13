package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.StringUtils.colorize
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Listens for player-related events.
 */
class PlayerListener : Base, Listener {
    init {
        if (config.getBoolean("chat.disable-join-messages", false)) {
            logger.info("Join messages are disabled in plugin config.")
        }
        if (config.getBoolean("chat.disable-leave-messages", false)) {
            logger.info("Leave messages are disabled in plugin config.")
        }
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (config.getBoolean("chat.disableJoinMessages", false)) {
            e.joinMessage = ""
        }
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        if (config.getBoolean("chat.disableJoinMessages", false)) {
            e.quitMessage = ""
        }
    }

    @EventHandler
    fun handleChatEvent(e: AsyncPlayerChatEvent) {
        chatManager.broadcastPlayerMessage(e.player, e.message)
        e.isCancelled = true
    }

    /**
     * Colorize sign text if players have permission.
     */
    @EventHandler
    fun handleSignPlacement(e: SignChangeEvent) {
        if (!e.player.hasPermission("stickychat.signColor")) {
            return
        }
        e.lines.forEachIndexed { index, s -> e.setLine(index, colorize(s)) }
    }
}
