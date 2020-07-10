package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.PlaceholderUtils
import com.dumbdogdiner.stickychat.utils.ServerUtils
import com.dumbdogdiner.stickychat.utils.StringUtils.colorize
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Listens for player-related events.
 */
class PlayerListener : Base, Listener {
    private val format = config.getString("chat.format", "&8%name%: %message%")!!

    init {
        if (config.getBoolean("chat.disableJoinMessages", false)) {
            ServerUtils.log("Join messages are disabled in plugin config.")
        }
        if (config.getBoolean("chat.disableJoinMessages", false)) {
            ServerUtils.log("Leave messages are disabled in plugin config.")
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
        val withFormatting = format
            .replace("%name%", if (e.player.hasPermission("stickychat.colorizeNick")) colorize(e.player.displayName) else e.player.displayName)
            .replace("%message%", if (e.player.hasPermission("stickychat.colorizeMessage")) colorize(e.message) else e.message)

        // This needs to be better
        e.format = PlaceholderUtils.setPlaceholdersSafe(
            e.player,
            withFormatting
        ).replace(Regex("/%.+?%/"), "")
    }
}
