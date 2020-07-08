package com.dumbdogdiner.stickychat.bungee.listeners

import com.dumbdogdiner.stickychat.Constants
import com.dumbdogdiner.stickychat.bungee.Base
import com.dumbdogdiner.stickychat.bungee.utils.ServerUtils
import com.google.common.io.ByteStreams
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

/**
 * Listener class for forwading messages to other servers.
 */
class ChatForwader : Base, Listener {
    @EventHandler
    fun forwardMessage(e: PluginMessageEvent) {
        ServerUtils.log("Received a plugin message on channel ${e.tag}.")

        if (e.tag != Constants.CHANNEL_NAME) {
            return
        }


        val dataIn = ByteStreams.newDataInput(e.data)
        val subChannel = dataIn.readUTF()

        logger.info("Received plugin message on '${Constants.CHANNEL_NAME}' $subChannel")
    }
}