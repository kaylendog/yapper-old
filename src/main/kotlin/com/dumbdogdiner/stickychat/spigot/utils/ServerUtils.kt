package com.dumbdogdiner.stickychat.spigot.utils

import com.dumbdogdiner.stickychat.Constants
import com.dumbdogdiner.stickychat.spigot.utils.StringUtils.colorize
import com.dumbdogdiner.stickychat.spigot.Base

/**
 * Utility methods in relation to the server.
 */
object ServerUtils : Base {
    fun log(message: String) = server.consoleSender.sendMessage(colorize("${Constants.CONSOLE_PREFIX}$message"))
    fun log(message: Throwable) = server.consoleSender.sendMessage(colorize("${Constants.ERROR_PREFIX}$message"))
}