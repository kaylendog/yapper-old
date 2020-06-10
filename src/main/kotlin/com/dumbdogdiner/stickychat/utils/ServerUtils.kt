package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.utils.StringUtils.colorize
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Utility methods in relation to the server.
 */
object ServerUtils {
    private val server
        get() = Bukkit.getServer()


    private const val consolePrefix = "&9StickyChat &8» "
    private const val errorPrefix = "&9StickyChat &eERROR &8» &r"

    fun log(message: String) = server.consoleSender.sendMessage("$consolePrefix${colorize(message)}")
    fun log(message: Throwable) = server.consoleSender.sendMessage("$errorPrefix$message")

    fun sendMessage(player: Player, message: String) {

    }
}