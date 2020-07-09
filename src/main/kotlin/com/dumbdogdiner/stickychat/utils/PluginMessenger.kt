package com.dumbdogdiner.stickychat.utils

import com.dumbdogdiner.stickychat.Base
import com.google.common.io.ByteStreams
import java.io.ByteArrayOutputStream
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Utility methods for plugin messaging.
 */
object PluginMessenger : Base {
    /**
     * Send a plugin message to Bungee.
     */
    private fun sendPluginMessage(type: MessageType, data: ByteArrayOutputStream) {
        sendTargetedPluginMessage(Bukkit.getOnlinePlayers().first(), type, data)
    }

    /**
     * Send a targeted plugin message via the specified player.
     */
    private fun sendTargetedPluginMessage(target: Player, type: MessageType, data: ByteArrayOutputStream) {
        val out = ByteStreams.newDataOutput()

        out.writeUTF("Forward")
        out.writeUTF("ALL")
        out.writeUTF(Constants.CHANNEL_NAME)

        out.write(type.ordinal)

        out.writeShort(data.toByteArray().size)
        out.write(data.toByteArray())

        target.sendPluginMessage(plugin, Constants.CHANNEL_NAME, out.toByteArray())
    }
}
