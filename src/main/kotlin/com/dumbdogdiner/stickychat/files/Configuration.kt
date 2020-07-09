package com.dumbdogdiner.stickychat.files

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils

/**
 * Utility methods for plugin configuration.
 */
object Configuration : Base {
    fun loadDefaultConfig() {
        ServerUtils.log("Loading configuration...")
        config.addDefault("postgres.database", "minecraft")
        config.addDefault("postgres.tableprefix", "stickychat_")
        config.addDefault("postgres.host", "localhost")
        config.addDefault("postgres.port", 5432)
        config.addDefault("postgres.username", "root")
        config.addDefault("postgres.password", "password")

        config.options().copyDefaults(true)
        plugin.saveConfig()
        plugin.reloadConfig()
    }
}
