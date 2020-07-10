package com.dumbdogdiner.stickychat.signspy

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.files.Language
import com.dumbdogdiner.stickychat.utils.BlockUtils
import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
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
                it.sendMessage(Language.signSpySignCreated)
            }
        }
    }

    @EventHandler
    fun handleSignPlacement(e: BlockPlaceEvent) {
        if (!config.getBoolean("sign-spy.enabled", true)) {
            return
        }

        if (!BlockUtils.isSign(e.block.type)) {
            return
        }
        ServerUtils.log("Player '${e.player.name}' (${e.player.uniqueId}) placed a sign at ${e.block.location}")
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
