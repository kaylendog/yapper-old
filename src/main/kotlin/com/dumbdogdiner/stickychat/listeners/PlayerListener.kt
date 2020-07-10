package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.PlaceholderUtils
import com.dumbdogdiner.stickychat.utils.StringUtils.colorize
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener : Base, Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
    }

    @EventHandler
    fun handleChatEvent(e: AsyncPlayerChatEvent) {
        var format = colorize(storageManager.getPlayerFormat(e.player))

        format = format
            .replace("%name%", if (e.player.hasPermission("stickychat.colorizeNick")) colorize(e.player.displayName) else e.player.displayName)
            .replace("%message%", if (e.player.hasPermission("stickychat.colorizeMessage")) colorize(e.message) else e.message)

        format = PlaceholderUtils.setPlaceholdersSafe(
            e.player,
            format
        )

        format.replace(Regex("/%.+?%/"), "%%%")

        e.format = format
    }
}
