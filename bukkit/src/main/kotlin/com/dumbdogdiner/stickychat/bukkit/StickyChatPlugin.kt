package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.integration.Integration
import com.dumbdogdiner.stickychat.api.util.Placeholders
import com.dumbdogdiner.stickychat.bukkit.broadcast.SkDeathMessageProvider
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
import com.dumbdogdiner.stickychat.bukkit.messaging.SkDirectMessageManager
import com.dumbdogdiner.stickychat.bukkit.models.Nicknames
<<<<<<< HEAD
<<<<<<< HEAD
import com.dumbdogdiner.stickychat.bukkit.util.ExposedLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.Bukkit
import org.bukkit.entity.Player
=======
import com.dumbdogdiner.stickychat.bukkit.messenger.RedisMessenger
=======
>>>>>>> ed29404... idek anymore
import com.dumbdogdiner.stickychat.bukkit.player.SkNicknameProvider
import com.dumbdogdiner.stickychat.bukkit.player.SkPlayerBlockManager
import com.dumbdogdiner.stickychat.bukkit.player.SkPriorityManager
import com.dumbdogdiner.stickychat.bukkit.util.ExposedLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.lang.Exception
<<<<<<< HEAD
>>>>>>> 55f7cd5... v4 :sparkles: major refactor :eyes:
import org.bukkit.plugin.Plugin
=======
>>>>>>> 7bbd84b... v4 :sparkles: not sure what these changes are
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

@kr.entree.spigradle.annotations.SpigotPlugin
class StickyChatPlugin : JavaPlugin() {
    companion object {
        lateinit var plugin: StickyChatPlugin
    }

<<<<<<< HEAD
<<<<<<< HEAD
    val integrationManager = StickyIntegrationManager()
    val channelManager = StickyChannelManager()
    val deathManager = StickyDeathMessageService()
=======
=======
    // managers
>>>>>>> d8d0ad7... v4 :sparkles: improvements to everything tbh
    val integrationManager = SkIntegrationManager()
    val channelManager = SkChannelManager()
<<<<<<< HEAD
    val deathManager = SkDeathMessageProvider()
<<<<<<< HEAD
>>>>>>> 55f7cd5... v4 :sparkles: major refactor :eyes:
=======
=======
>>>>>>> d8d0ad7... v4 :sparkles: improvements to everything tbh
    val directMessageManager = SkDirectMessageManager()
>>>>>>> 9dc7aa2... v4 :sparkles: rewrite NicknameProvider

    // data stores
    val deathMessageProvider = SkDeathMessageProvider()
    val playerBlockManager = SkPlayerBlockManager()
    val priorityManager = SkPriorityManager()
    val nicknameProvider = SkNicknameProvider()

    // sql related vars
    var sqlEnabled = false

    // plugin integration
    lateinit var integration: Integration

    override fun onLoad() {
        plugin = this
        // load configuration
        saveDefaultConfig()
        reloadConfig()
        // register API service
        StickyChat.registerService(this, ApiProvider)
    }

    override fun onEnable() {
        getCommand("chat")?.setExecutor(ChatCommand())
        getCommand("message")?.setExecutor(MessageCommand())
        getCommand("reply")?.setExecutor(ReplyCommand())
        getCommand("nickname")?.setExecutor(NicknameCommand())
        getCommand("channel")?.setExecutor(ChannelCommand())
        getCommand("staffchat")?.setExecutor(StaffChatCommand())

<<<<<<< HEAD
        val integration = this.integrationManager.getIntegration(this)
        integration.prefix = this.config.getString("chat.prefix", "&b&lStickyChat &r&8» &r")!!

=======
        this.integration = this.integrationManager.getIntegration(this)
        this.integration.prefix = this.config.getString("chat.prefix", "&b&lStickyChat &r&8» &r")!!

        // check if sql database has been enabled
>>>>>>> d8d0ad7... v4 :sparkles: improvements to everything tbh
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
            Database.connect(dataSource)

            transaction {
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

        // register event listeners
        this.server.pluginManager.registerEvents(MessageListener(), this)
        this.server.pluginManager.registerEvents(PlayerJoinQuitListener(), this)
        this.server.pluginManager.registerEvents(DeathListener(), this)

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
<<<<<<< HEAD

    override fun getProvider(): Plugin {
        return this
    }
=======
>>>>>>> d8d0ad7... v4 :sparkles: improvements to everything tbh
}
