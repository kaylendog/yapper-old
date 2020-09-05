package com.dumbdogdiner.stickychatbukkit.utils

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils.colorize
import org.bukkit.HeightMap
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.util.Vector

/**
 * Utility methods in relation to the server.
 */
object ServerUtils : Base {
    fun sendMessage(sender: CommandSender, message: String) = sender.sendMessage("${colorize(config.getString("prefix").orEmpty())}$message")
    fun sendColorizedMessage(sender: CommandSender, message: String) =
        sendMessage(sender, colorize(message))

    fun extractLocation(a: Location, b: Vector): Location {
        return a.world!!.getHighestBlockAt(a.clone().add(
                b
                        .clone()
                        .normalize()
                        .multiply(-3)
        ), HeightMap.WORLD_SURFACE).location.clone()
            .add(0.0, 1.5, 0.0)
    }
}
