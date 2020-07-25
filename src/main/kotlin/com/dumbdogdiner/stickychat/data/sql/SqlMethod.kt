package com.dumbdogdiner.stickychat.data.sql

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.Letter
import com.dumbdogdiner.stickychat.data.StorageMethod
import com.dumbdogdiner.stickychat.data.sql.models.Letters
import com.dumbdogdiner.stickychat.data.sql.models.Nicknames
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.properties.Delegates

/**
 * Manages the connection between PostgreSQL and the plugin.
 */
abstract class SqlMethod : Base, StorageMethod {
    abstract val driver: String
    abstract val protocol: String

    private lateinit var host: String
    private var port by Delegates.notNull<Int>()
    private lateinit var database: String

    private lateinit var user: String
    private lateinit var password: String

    private lateinit var db: Database

    /**
     * Initialize the database connection.
     */
    override fun init() {
        host = config.getString("data.host")!!
        port = config.getInt("data.port")

        database = config.getString("data.database")!!
        user = config.getString("data.username")!!
        password = config.getString("data.password")!!

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
                    Letters, Nicknames
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
        return transaction {
            Nicknames.select {  Nicknames.id eq player.uniqueId.toString() }.singleOrNull()?.get(Nicknames.value)
        }
    }

    override fun setPlayerNickname(player: Player, new: String): Boolean {
        transaction {
            Nicknames.insert {
                it[id] = player.uniqueId.toString()
                it[value] = new
            }
        }
        return true
    }

    override fun clearPlayerNickname(player: Player): Boolean {
        return transaction {
            Nicknames.deleteWhere { Nicknames.id eq player.uniqueId.toString() } != 0
        }
    }

    override fun saveLetter(from: Player, to: Player, content: String, createdAt: Long): Boolean {
        transaction {
            Letters.insert {
                it[fromUuid] = from.uniqueId.toString()
                it[fromName] = from.name
                it[toName] = to
                it[toUuid] = toUuid
                it[content] = content
                it[createdAt] = createdAt
            }
        }
        return true
    }

    override fun savePartialLetter(from: Player, toName: String, content: String, createdAt: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLetter(id: Int): Letter? {
        val letter = transaction {
            Letters.select { Letters.id eq id }.singleOrNull()
        } ?: return null

        return Letter()
    }
}
