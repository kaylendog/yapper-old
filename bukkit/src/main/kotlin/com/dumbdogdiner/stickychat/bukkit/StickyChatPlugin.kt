package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.DataService
import com.dumbdogdiner.stickychat.api.Formatter
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.chat.DirectMessageService
import com.dumbdogdiner.stickychat.api.chat.MessageService
import com.dumbdogdiner.stickychat.api.chat.StaffChatService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyDirectMessageService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyMessageService
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class StickyChatPlugin : StickyChat, JavaPlugin() {
    override fun onLoad() {
        logger.info("Registering chat service...")
        StickyChat.registerService(this, this)
    }

    override fun onEnable() {
        logger.info("Setting up services...")
    }

    override fun getMessageService(player: Player): MessageService {
        this.logger.fine("Accessing message service for player '${player.uniqueId}'...")
        return StickyMessageService.get(player)
    }

    override fun getDirectMessageService(player: Player): DirectMessageService {
        this.logger.fine("Accessing direct message service for player '${player.uniqueId}'...")
        return StickyDirectMessageService.get(player)
    }

    override fun getStaffChatService(player: Player): StaffChatService {
        TODO("Not yet implemented")
    }

    override fun getDataService(player: Player): DataService {
        this.logger.fine("Accessing data service for player '${player.uniqueId}'...")
        return StickyDataService.get(player)
    }

    override fun getDataServices(): MutableList<DataService> {
        return Bukkit.getOnlinePlayers().map { getDataService(it) }.toMutableList()
    }

    /**
     * Get the formatter for the target player.
     */
    override fun getFormatter(player: Player): Formatter {
        return StickyFormatter.get(player)
    }

    override fun disableChat(): Boolean {
        TODO("Not yet implemented")
    }

    override fun enableChat(): Boolean {
        TODO("Not yet implemented")
    }
}
