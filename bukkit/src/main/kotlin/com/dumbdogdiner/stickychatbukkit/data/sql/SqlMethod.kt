package com.dumbdogdiner.stickychatbukkit.data.sql

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.data.Letter
import com.dumbdogdiner.stickychatbukkit.data.StorageMethod
import com.dumbdogdiner.stickychatbukkit.data.sql.models.Letters
import com.dumbdogdiner.stickychatbukkit.data.sql.models.Nicknames
import kotlin.properties.Delegates
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/**
 * Manages the connection between PostgreSQL and the plugin.
 */
abstract class SqlMethod : Base,
    StorageMethod {
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
                    Letters,
                    Nicknames
                )
                logger.info("[sql] Database ready.")
            }
        } catch (e: Exception) {
            logger.severe(e.localizedMessage)
            logger.severe("[sql] Failed to connect to server - will unload.")
            plugin.heckDontEnableSomethingWentWrong = true
            server.pluginManager.disablePlugin(plugin)
        }
    }

    override fun getPlayerNickname(player: Player): String? {
        return transaction {
            Nicknames.select { Nicknames.id eq player.uniqueId.toString() }.singleOrNull()?.get(
                Nicknames.value)
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

    override fun savePartialLetter(from: Player, toName: String, content: String, createdAt: Long): Boolean {
        transaction {
            Letters.insert {
                it[fromUuid] = from.uniqueId.toString()
                it[fromName] = from.name
                it[Letters.toName] = toName
                it[Letters.content] = content
                it[Letters.createdAt] = createdAt
                it[unread] = true
            }
        }
        return true
    }

    override fun hydratePartialLetter(fromUuid: String, fromName: String, to: Player, createdAt: Long): Boolean {
        transaction {
            Letters.update({
                Letters.fromUuid.eq(fromUuid) and Letters.toName.eq(to.name) and Letters.createdAt.eq(createdAt)
            }) {
                it[toUuid] = to.uniqueId.toString()
            }
        }
        return true
    }

    override fun getLetter(id: Int): Letter? {
        val letter = transaction {
            Letters.select { Letters.id eq id }.singleOrNull()
        } ?: return null

        return Letter(
            letter[Letters.fromUuid],
            letter[Letters.fromName],
            letter[Letters.toUuid],
            letter[Letters.toName],
            letter[Letters.content],
            letter[Letters.createdAt]
        )
    }

    override fun fetchLettersForPlayer(player: Player): List<Letter> {
        return transaction {
            Letters
                .select { Letters.toUuid eq player.uniqueId.toString() }
                .map {
                    Letter(
                        it[Letters.fromUuid],
                        it[Letters.fromName],
                        it[Letters.toUuid],
                        it[Letters.toName],
                        it[Letters.content],
                        it[Letters.createdAt]
                    )
                }
        }
    }

    override fun fetchLettersForPlayer(player: Player, filterUnread: Boolean): List<Letter> {
        return transaction {
            Letters
                .select { Letters.toUuid.eq(player.uniqueId.toString()) and Letters.unread.eq(true) }
                .map {
                    Letter(
                        it[Letters.fromUuid],
                        it[Letters.fromName],
                        it[Letters.toUuid],
                        it[Letters.toName],
                        it[Letters.content],
                        it[Letters.createdAt]
                    )
                }
        }
    }
}
