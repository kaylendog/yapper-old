package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.StringUtils.colorize

/**
 * Utility methods in relation to the server.
 */
object ServerUtils : Base {
    fun log(message: String) = server.consoleSender.sendMessage(colorize("${Constants.CONSOLE_PREFIX}$message"))
    fun log(message: Throwable) = server.consoleSender.sendMessage(colorize("${Constants.ERROR_PREFIX}$message"))
}
