package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.files.Language
import com.dumbdogdiner.stickychat.utils.StringUtils.colorize
import org.bukkit.command.CommandSender

/**
 * Utility methods in relation to the server.
 */
object ServerUtils : Base {
    fun sendMessage(sender: CommandSender, message: String) = sender.sendMessage("${Language.prefix}$message")

    fun log(message: String) = server.consoleSender.sendMessage(colorize("${Constants.CONSOLE_PREFIX}$message"))
    fun log(message: Throwable) = server.consoleSender.sendMessage(colorize("${Constants.ERROR_PREFIX}$message"))
}
