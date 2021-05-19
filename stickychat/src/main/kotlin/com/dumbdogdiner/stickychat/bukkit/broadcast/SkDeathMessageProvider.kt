package com.dumbdogdiner.stickychat.bukkit.broadcast

import com.dumbdogdiner.stickychat.api.broadcast.DeathMessageProvider
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.util.HashMap
import org.bukkit.event.entity.EntityDamageEvent
import java.io.File

class SkDeathMessageProvider : WithPlugin, DeathMessageProvider {
    private lateinit var deathConfiguration: FileConfiguration
    private var enabled = false

    private val messages = hashMapOf<EntityDamageEvent.DamageCause, Array<String>>()

    fun initialize() {
        val path = File(plugin.dataFolder, "deaths.yml")

        // save default config if it doesn't exist
        if (!path.exists()) {
            this.plugin.saveResource("deaths.yml", false)
        }

        deathConfiguration = YamlConfiguration()
        try {
            deathConfiguration.load(path)
            this.logger.info("Loaded ${deathConfiguration.getKeys(false).size} death messages from configuration")
            enabled = true

            // load messages into memory.
            deathConfiguration.getKeys(false).forEach {
                val type = EntityDamageEvent.DamageCause.valueOf(it.toUpperCase().replace("-", "_"))
                if (type != null) {
                    messages[type] = deathConfiguration.getStringList(it).toTypedArray()
                }
            }
        } catch (e: Exception) {
            this.logger.warning("Failed to load death message configuration - perhaps the config file is malformed?")
            e.printStackTrace()
        }
    }

    override fun getDeathMessages(): HashMap<EntityDamageEvent.DamageCause, Array<String>> {
        return HashMap(this.messages)
    }
}
