package com.dumbdogdiner.stickychat.spigot.utils

import com.dumbdogdiner.stickychat.Constants
import com.dumbdogdiner.stickychat.spigot.Base
import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

/**
 * Utility methods for plugin messaging.
 */
object PluginMessenger : Base {
    fun sendPluginMessage() {
        val out = ByteStreams.newDataOutput()

        out.writeUTF("owo")

        Bukkit.getOnlinePlayers().firstOrNull()
            ?.sendPluginMessage(plugin, Constants.CHANNEL_NAME, out.toByteArray())

        ServerUtils.log("Sent plugin message via ${Constants.CHANNEL_NAME}")
    }
}