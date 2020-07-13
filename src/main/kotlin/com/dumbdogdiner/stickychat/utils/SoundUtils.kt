package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import com.okkero.skedule.BukkitDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bukkit.Sound
import org.bukkit.entity.Player

/**
 * Utility methods for sending adorable fox notification sounds omg this was such a good idea i can't~
 */
object SoundUtils : Base {
    fun success(player: Player) {
        GlobalScope.launch(BukkitDispatcher(plugin)) {
            playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f)
            delay(500)
            playSound(player, Sound.ENTITY_FOX_SCREECH)
        }
    }

    /**
     * Notification sound.
     */
    fun info(player: Player) {
        GlobalScope.launch(BukkitDispatcher(plugin)) {
            playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1.5f)
            delay(500)
            playSound(player, Sound.ENTITY_FOX_SLEEP)
        }
    }

    /**
     * Quieter sound, reserved for less important notifications.
     */
    fun quiet(player: Player) {
        GlobalScope.launch(BukkitDispatcher(plugin)) {
            playSound(player, Sound.ENTITY_FOX_AMBIENT)
        }
    }

    /**
     * Error notification - OMG YOU HURT THE FOX >:CC
     */
    fun error(player: Player) {
        GlobalScope.launch(BukkitDispatcher(plugin)) {
            playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS)
            playSound(player, Sound.ENTITY_ITEM_BREAK)
            playSound(player, Sound.ENTITY_FOX_HURT)
        }
    }

    private fun playSound(player: Player, soundName: Sound, pitch: Float = 1f) {
        if (plugin.config.getBoolean("disableSound")) {
            return
        }

        player.playSound(player.location, soundName, 1f, pitch)
    }
}
