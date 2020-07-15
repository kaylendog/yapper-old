package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.FormatUtils.colorize
import com.dumbdogdiner.stickychat.utils.SoundUtils
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

/**
 * Class for dealing with Sign changes.
 */
class SignSpyManager : Listener, Base {
    private val disabledPlayers = mutableSetOf<Player>()

    /**
     * Handle the creation of a sign.
     */
    fun handleSignCreation(e: SignChangeEvent) {
        for (it in server.onlinePlayers) {
            if (!it.hasPermission("stickychat.signspy")) {
                continue
            }

            it.sendMessage(
                colorize("&bPlayer '%name%' (%uuid%) placed a sign at %x%, %y%, %z% with content: %content%")
                    .replace("%name%", e.player.displayName)
                    .replace("%uuid%", e.player.uniqueId.toString())
                    .replace("%x%", e.block.location.x.toString())
                    .replace("%y%", e.block.location.y.toString())
                    .replace("%z%", e.block.location.z.toString())
                    .replace("%content%", e.lines.filter { txt -> txt.isNotBlank() }.joinToString(","))
            )

            SoundUtils.info(it)
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
