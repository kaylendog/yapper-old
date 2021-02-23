package com.dumbdogdiner.stickychat.bukkit.broadcast

import com.dumbdogdiner.stickychat.api.broadcast.DeathMessageProvider
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import java.util.HashMap
import org.bukkit.event.entity.EntityDamageEvent

<<<<<<< HEAD:bukkit/src/main/kotlin/com/dumbdogdiner/stickychat/bukkit/misc/StickyDeathMessageService.kt
<<<<<<< HEAD
class StickyDeathMessageService : WithPlugin, DeathMessageService {
    private lateinit var deathConfiguration: FileConfiguration
=======
class StickyDeathMessageService : WithPlugin, DeathMessageManager {
=======
class SkDeathMessageProvider : WithPlugin, DeathMessageProvider {
<<<<<<< HEAD
>>>>>>> 55f7cd5... v4 :sparkles: major refactor :eyes::bukkit/src/main/kotlin/com/dumbdogdiner/stickychat/bukkit/broadcast/SkDeathMessageProvider.kt
    private var deathConfiguration: FileConfiguration
>>>>>>> c993afb... v4 :sparkles: api rewrite
    private var enabled = false

    private val messages = hashMapOf<EntityDamageEvent.DamageCause, Array<String>>()

    public fun initialize() {
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

    override fun getAllDeathMessages(): HashMap<EntityDamageEvent.DamageCause, Array<String>> {
        return this.messages
=======
    override fun getDeathMessages(): HashMap<EntityDamageEvent.DamageCause, Array<String>> {
        TODO("Not yet implemented")
>>>>>>> ed29404... idek anymore
    }
}
