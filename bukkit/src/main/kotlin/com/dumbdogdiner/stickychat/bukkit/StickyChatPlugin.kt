package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.DataService
import com.dumbdogdiner.stickychat.api.Formatter
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.chat.ChannelManager
import com.dumbdogdiner.stickychat.api.chat.DirectMessageService
import com.dumbdogdiner.stickychat.api.chat.MessageService
import com.dumbdogdiner.stickychat.api.chat.NicknameService
import com.dumbdogdiner.stickychat.api.chat.StaffChatService
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager
import com.dumbdogdiner.stickychat.api.misc.BroadcastService
import com.dumbdogdiner.stickychat.api.misc.DeathMessageService
import com.dumbdogdiner.stickychat.api.util.Placeholders
import com.dumbdogdiner.stickychat.bukkit.chat.StickyChannelManager
import com.dumbdogdiner.stickychat.bukkit.chat.StickyDirectMessageService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyMessageService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyNicknameService
import com.dumbdogdiner.stickychat.bukkit.chat.StickyStaffChatService
import com.dumbdogdiner.stickychat.bukkit.commands.ChannelCommand
import com.dumbdogdiner.stickychat.bukkit.commands.ChatCommand
import com.dumbdogdiner.stickychat.bukkit.commands.MessageCommand
import com.dumbdogdiner.stickychat.bukkit.commands.NicknameCommand
import com.dumbdogdiner.stickychat.bukkit.commands.ReplyCommand
import com.dumbdogdiner.stickychat.bukkit.commands.StaffChatCommand
import com.dumbdogdiner.stickychat.bukkit.integration.StickyIntegrationManager
import com.dumbdogdiner.stickychat.bukkit.listeners.DeathListener
import com.dumbdogdiner.stickychat.bukkit.listeners.MessageListener
import com.dumbdogdiner.stickychat.bukkit.listeners.PlayerJoinQuitListener
import com.dumbdogdiner.stickychat.bukkit.misc.StickyDeathMessageService
import com.dumbdogdiner.stickychat.bukkit.models.Nicknames
import com.dumbdogdiner.stickychat.bukkit.util.ExposedLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

@kr.entree.spigradle.annotations.SpigotPlugin
class StickyChatPlugin : StickyChat, JavaPlugin() {
    companion object {
        lateinit var plugin: StickyChatPlugin
    }

    val integrationManager = StickyIntegrationManager()
    val channelManager = StickyChannelManager()
    val deathManager = StickyDeathMessageService()

    var sqlEnabled = false
    lateinit var db: Database

    override fun onLoad() {
        plugin = this
        // load configuration
        saveDefaultConfig()
        reloadConfig()

        this.logger.info("Registering chat service...")
        StickyChat.registerService(this, this)
    }

    override fun onEnable() {
        getCommand("chat")?.setExecutor(ChatCommand())
        getCommand("message")?.setExecutor(MessageCommand())
        getCommand("reply")?.setExecutor(ReplyCommand())
        getCommand("nickname")?.setExecutor(NicknameCommand())
        getCommand("channel")?.setExecutor(ChannelCommand())
        getCommand("staffchat")?.setExecutor(StaffChatCommand())

        val integration = this.integrationManager.getIntegration(this)
        integration.prefix = this.config.getString("chat.prefix", "&b&lStickyChat &r&8» &r")!!

        if (this.config.getBoolean("data.enable", true)) {
            this.logger.info("[SQL] Checking SQL database has been set up correctly...")

            val config = HikariConfig().apply {
                jdbcUrl = "jdbc:postgresql://${
                    config.getString("data.host")
                }:${
                    config.getInt("data.port")
                }/${
                    config.getString("data.database")
                }"
                driverClassName = "com.dumbdogdiner.stickychat.libs.org.postgresql.Driver"
                username = config.getString("data.username", "postgres")!!
                password = config.getString("data.password")!!
                maximumPoolSize = 2
            }

            val dataSource = HikariDataSource(config)
            this.db = Database.connect(dataSource)

            transaction(this.db) {
                try {
                    addLogger(ExposedLogger())
                    SchemaUtils.createMissingTablesAndColumns(Nicknames)
                    sqlEnabled = true
                } catch (e: Exception) {
                    logger.warning("[SQL] Failed to connect to SQL database - invalid connection info/database not up")
                    e.printStackTrace()
                    logger.warning("[SQL] Persistent storage via SQL has been disabled")
                }
            }
        } else {
            this.logger.warning("SQL database has been disabled - expect unexpected side effects!")
        }

        server.pluginManager.registerEvents(MessageListener(), this)
        server.pluginManager.registerEvents(PlayerJoinQuitListener(), this)
        server.pluginManager.registerEvents(DeathListener(), this)

        // initialize the death message service
        this.deathManager.initialize()

        // check for PlaceholderAPI.
        if (Placeholders.hasPlaceholderApiEnabled()) {
            PapiExtension().register()
        } else {
            this.logger.warning("[PAPI] PlaceholderAPI not found - placeholders have been disabled")
        }

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

    override fun getChannelManager(): ChannelManager {
        return this.channelManager
    }

    override fun getBroadcastService(): BroadcastService {
        TODO("Not yet implemented")
    }

    override fun getDeathMessageService(): DeathMessageService {
        return this.deathManager
    }

    override fun getFormatter(player: Player): Formatter {
        return StickyFormatter.get(player)
    }

    override fun getIntegrationManager(): IntegrationManager {
        return this.integrationManager
    }

    override fun disableChat(): Boolean {
        return false
    }

    override fun enableChat(): Boolean {
        return false
    }
}
