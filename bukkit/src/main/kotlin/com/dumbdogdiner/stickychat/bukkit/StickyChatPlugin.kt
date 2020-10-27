package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.DataService
import com.dumbdogdiner.stickychat.api.Formatter
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.chat.DirectMessageService
import com.dumbdogdiner.stickychat.api.chat.MessageService
import com.dumbdogdiner.stickychat.api.chat.NicknameService
import com.dumbdogdiner.stickychat.api.chat.StaffChatService
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager
import com.dumbdogdiner.stickychat.api.misc.BroadcastService
import com.dumbdogdiner.stickychat.api.misc.DeathMessageService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyDirectMessageService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyMessageService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyNicknameService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyStaffChatService
import com.dumbdogdiner.stickychat.bukkit.commands.MessageCommand
import com.dumbdogdiner.stickychat.bukkit.commands.ReplyCommand
import com.dumbdogdiner.stickychat.bukkit.commands.VersionCommand
import com.dumbdogdiner.stickychat.bukkit.integration.StickyIntegrationManager
import com.dumbdogdiner.stickychat.bukkit.listeners.MessageListener
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

@kr.entree.spigradle.annotations.SpigotPlugin
class StickyChatPlugin : StickyChat, JavaPlugin() {
    companion object {
        lateinit var plugin: StickyChatPlugin
    }

    val integrationManager = StickyIntegrationManager()

    override fun onLoad() {
        plugin = this
        logger.info("Registering chat service...")
        StickyChat.registerService(this, this)
    }

    override fun onEnable() {
        logger.info("Registering commands...")
        getCommand("version")?.setExecutor(VersionCommand())
        getCommand("message")?.setExecutor(MessageCommand())
        getCommand("reply")?.setExecutor(ReplyCommand())

        logger.info("Setting up self-hosted API integration...")
        val integration = this.integrationManager.getIntegration(this)
        integration.prefix = "&d&lStickyChat &r&8>&r "

        logger.info("Registering events...")
        server.pluginManager.registerEvents(MessageListener(), this)

        logger.info("Done")
    }

    override fun getProvider(): Plugin {
        return this
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
        return StickyStaffChatService.get(player)
    }

    override fun getNicknameService(player: Player): NicknameService {
        return StickyNicknameService.get(player)
    }

    override fun getDataService(player: Player): DataService {
        this.logger.fine("Accessing data service for player '${player.uniqueId}'...")
        return StickyDataService.get(player)
    }

    override fun getDataServices(): MutableList<DataService> {
        return Bukkit.getOnlinePlayers().map { getDataService(it) }.toMutableList()
    }

    override fun getBroadcastService(): BroadcastService {
        TODO("Not yet implemented")
    }

    override fun getDeathMessageService(): DeathMessageService {
        TODO("Not yet implemented")
    }

    /**
     * Get the formatter for the target player.
     */
    override fun getFormatter(player: Player): Formatter {
        return StickyFormatter.get(player)
    }

    override fun getIntegrationManager(): IntegrationManager {
        return this.integrationManager
    }

    override fun disableChat(): Boolean {
        TODO("Not yet implemented")
    }

    override fun enableChat(): Boolean {
        TODO("Not yet implemented")
    }
}
