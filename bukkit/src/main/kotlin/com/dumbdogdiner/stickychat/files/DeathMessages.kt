package com.dumbdogdiner.stickychat.files

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.FormatUtils
import com.dumbdogdiner.stickychat.utils.PlaceholderUtils
import java.io.File
import java.lang.Exception
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent

object DeathMessages : Base {
    private val deathConfig = YamlConfiguration()

    /**
     * Load the config messages.
     */
    fun loadMessages() {
        logger.info("Loading custom death message configuration...")
        val file = File(plugin.dataFolder, "death-messages.yml")
        if (!file.exists()) {
            logger.info("Death message config file did not exist - made it")
            plugin.saveResource("death-messages.yml", false)
        }

        try {
            deathConfig.load(file)
        } catch (e: Exception) {
            logger.warning("Failed to load custom death message configuration")
            e.printStackTrace()
        }
    }

    /**
     * Get a random formatted death message.
     */
    fun getRandomFormatted(player: Player, type: EntityDamageEvent.DamageCause, attacker: Entity?): String? {
        var message = getRandom(type) ?: return null

        message = message
            .replace("%name%", player.name)
            .replace("%displayname%", storageManager.getPlayerDisplayname(player))

        logger.info((attacker == null).toString())

        if (attacker != null) {
            message = message.replace("%attacker%", attacker.name)
        }

        return PlaceholderUtils.setPlaceholdersSafe(player, FormatUtils.colorize(message))
    }

    /**
     * Get a random death message from configuration.
     */
    fun getRandom(type: EntityDamageEvent.DamageCause): String? {
        val section = deathConfig.getList(
            formatEnumName(type)
        ) ?: return null
        return section.random() as String
    }

    /**
     * Fetch all the loaded death messages.
     */
    fun getAll(type: EntityDamageEvent.DamageCause): List<String>? {
        val section = deathConfig.getList(
            formatEnumName(type)
        ) ?: return null
        return section as List<String>
    }

    private fun formatEnumName(type: EntityDamageEvent.DamageCause): String = type.name.toLowerCase().replace("_", "-")
}
