package com.dumbdogdiner.stickychat.data.sql

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.StorageMethod
import com.dumbdogdiner.stickychat.data.sql.models.MailMessages
import com.dumbdogdiner.stickychat.data.sql.models.Nicknames
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Manages the connection between PostgreSQL and the plugin.
 */
abstract class SqlMethod : Base, StorageMethod {
    abstract val driver: String
    abstract val protocol: String

    private val host: String
    private val port: Int
    private val database: String

    private val user: String
    private val password: String

    private lateinit var db: Database

    init {
        host = config.getString("data.host")!!
        port = config.getInt("data.port")

        database = config.getString("data.database")!!
        user = config.getString("data.username")!!
        password = config.getString("data.password")!!
    }

    /**
     * Initialize the database connection.
     */
    override fun init() {
        logger.info("[sql] Setting up sql connection...")
        try {
            db = Database.connect(
                "$protocol://$host:$port/$database",
                user = user,
                password = password,
                driver = driver
            )
            transaction {
                addLogger(SqlLogger())
                SchemaUtils.createMissingTablesAndColumns(
                    MailMessages, Nicknames
                )
                logger.info("[sql] Database ready.")
            }
        } catch (e: Exception) {
            logger.severe(e.localizedMessage)
            logger.info("Failed to connect to server.")
            server.pluginManager.disablePlugin(plugin)
        }
    }

    override fun getPlayerNickname(player: Player): String? {
        TODO("Not yet implemented")
    }

    override fun setPlayerNickname(player: Player, new: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearPlayerNickname(player: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun saveMailMessage(from: Player, to: String, content: String, created: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMailMessage(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}
