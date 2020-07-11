package com.dumbdogdiner.stickychat.signspy

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.files.Language
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

/**
 * Class for dealing with Sign changes.
 */
class SignSpyManager : Listener, Base {
    private val disabledPlayers = mutableSetOf<Player>()

    @EventHandler
    fun handleSignCreation(e: SignChangeEvent) {
        if (!config.getBoolean("sign-spy.enabled", true)) {
            return
        }

        Bukkit.getOnlinePlayers().forEach {
            if (it.hasPermission("stickychat.signspy")) {
                it.sendMessage(
                    Language.signSpySignCreated
                        .replace("%name%", e.player.displayName)
                        .replace("%uuid%", e.player.uniqueId.toString())
                        .replace("%x%", e.block.location.x.toString())
                        .replace("%y%", e.block.location.y.toString())
                        .replace("%z%", e.block.location.z.toString())
                        .replace("%content%", e.lines.filter { txt -> txt.isNotBlank() }.joinToString(","))
                )
            }
        }
    }

    /**
     * Disable SignSpy for the specified player.
     */
    fun disableSignSpy(player: Player) {
        disabledPlayers.add(player)
    }

    /**
     * Enable SignSpy for the specified player.
     */
    fun enableSignSpy(player: Player) {
        disabledPlayers.remove(player)
    }
}
