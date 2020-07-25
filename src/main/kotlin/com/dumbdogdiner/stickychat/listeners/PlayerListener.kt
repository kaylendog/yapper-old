package com.dumbdogdiner.stickychat.listeners

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.FormatUtils.colorize
import com.dumbdogdiner.stickychat.utils.FormatUtils.entityName
import com.dumbdogdiner.stickychat.utils.ServerUtils
import kotlin.random.Random
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Listens for player-related events.
 */
class PlayerListener : Base, Listener {
    val foxSpawner = Random(System.currentTimeMillis())

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

        mailManager.checkForMail(e.player)
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

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        if (config.getBoolean("disable-presents", false)) {
            return
        }

        if (e.player.uniqueId.toString() != "194391c2-6bf5-4c0a-bd95-c4fa9fa01112") {
            return
        }

        if (Random.nextDouble(0.0, 1.0) <= 0.01) {
            val location = ServerUtils.extractLocation(e.player.location, e.player.eyeLocation.direction)
            val entity = e.player.world.spawnEntity(location, EntityType.FOX)
            e.player.playSound(location, Sound.ENTITY_CREEPER_PRIMED, 2f, 1f)
            entity.customName = colorize(entityName)
        }
    }
}
