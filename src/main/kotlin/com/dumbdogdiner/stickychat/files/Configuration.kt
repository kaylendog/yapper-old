package com.dumbdogdiner.stickychat.files

import com.dumbdogdiner.stickychat.Base

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
