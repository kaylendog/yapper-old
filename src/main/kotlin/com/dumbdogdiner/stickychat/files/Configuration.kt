package com.dumbdogdiner.stickychat.files

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils

/**
 * Utility methods for plugin configuration.
 */
object Configuration : Base {
    fun loadDefaultConfig() {
        logger.info("Loading configuration...")
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
    }
}
