package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.util.Placeholders
import com.dumbdogdiner.stickychat.bukkit.channel.SkChannelManager
import com.dumbdogdiner.stickychat.bukkit.commands.ChannelCommand
import com.dumbdogdiner.stickychat.bukkit.commands.ChatCommand
import com.dumbdogdiner.stickychat.bukkit.commands.MessageCommand
import com.dumbdogdiner.stickychat.bukkit.commands.NicknameCommand
import com.dumbdogdiner.stickychat.bukkit.commands.ReplyCommand
import com.dumbdogdiner.stickychat.bukkit.commands.StaffChatCommand
import com.dumbdogdiner.stickychat.bukkit.integration.SkIntegrationManager
import com.dumbdogdiner.stickychat.bukkit.listeners.DeathListener
import com.dumbdogdiner.stickychat.bukkit.listeners.MessageListener
import com.dumbdogdiner.stickychat.bukkit.listeners.PlayerJoinQuitListener
import com.dumbdogdiner.stickychat.bukkit.broadcast.SkDeathMessageProvider
import com.dumbdogdiner.stickychat.bukkit.models.Nicknames
<<<<<<< HEAD
import com.dumbdogdiner.stickychat.bukkit.util.ExposedLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.Bukkit
import org.bukkit.entity.Player
=======
import com.dumbdogdiner.stickychat.bukkit.messenger.RedisMessenger
import com.dumbdogdiner.stickychat.bukkit.util.ExposedLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.lang.Exception
>>>>>>> 55f7cd5... v4 :sparkles: major refactor :eyes:
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

<<<<<<< HEAD
    val integrationManager = StickyIntegrationManager()
    val channelManager = StickyChannelManager()
    val deathManager = StickyDeathMessageService()
=======
    val integrationManager = SkIntegrationManager()
    val redisMessenger = RedisMessenger()
    val channelManager = SkChannelManager()
    val deathManager = SkDeathMessageProvider()
<<<<<<< HEAD
>>>>>>> 55f7cd5... v4 :sparkles: major refactor :eyes:
=======
    val directMessageManager = SkDirectMessageManager()
>>>>>>> 9dc7aa2... v4 :sparkles: rewrite NicknameProvider

    var sqlEnabled = false
    lateinit var db: Database

    override fun onLoad() {
        plugin = this
        // load configuration
        saveDefaultConfig()
        reloadConfig()

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
}
