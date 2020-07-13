package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.StringUtils.colorize
import org.bukkit.command.CommandSender

/**
 * Utility methods in relation to the server.
 */
object ServerUtils : Base {
    fun sendMessage(sender: CommandSender, message: String) = sender.sendMessage("${colorize(config.getString("prefix").orEmpty())}$message")
    fun sendColorizedMessage(sender: CommandSender, message: String) = sendMessage(sender, colorize(message))
}
