package com.dumbdogdiner.stickychatbukkit.data.sql

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.data.Letter
import com.dumbdogdiner.stickychatbukkit.data.StorageMethod
import com.dumbdogdiner.stickychatbukkit.data.sql.models.Letters
import com.dumbdogdiner.stickychatbukkit.data.sql.models.Nicknames
import com.google.gson.Gson
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
        try {
            return transaction {
                Nicknames.select { Nicknames.id eq player.uniqueId.toString() }.singleOrNull()?.get(
                        Nicknames.value)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun setPlayerNickname(player: Player, new: String?): Boolean {
        transaction {
            val updated = Nicknames.update({ Nicknames.id.eq(player.uniqueId.toString()) }) {
                it[value] = new
            }

            // if found a result
            if (updated != 0) {
                return@transaction
            }

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

    override fun savePartialLetter(from: Player, toName: String, title: String, pages: List<String>, createdAt: Long): Boolean {
        val data = Gson().toJson(pages)

        transaction {
            Letters.insert {
                it[fromUuid] = from.uniqueId.toString()
                it[fromName] = from.name
                it[Letters.toName] = toName
                it[Letters.title] = title
                it[Letters.pages] = data.toString()
                it[Letters.createdAt] = createdAt
                it[unread] = true
            }
        }
        return true
    }

    override fun hydratePartialLetters(to: Player) {
        transaction {
            Letters.update({
                Letters.toName.eq(to.name)
            }) {
                it[toUuid] = to.uniqueId.toString()
            }
        }
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
            letter[Letters.title],
            Gson().fromJson<List<String>>(letter[Letters.pages], List::class.java),
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
                        it[Letters.title],
                        Gson().fromJson<List<String>>(it[Letters.pages], List::class.java),
                        it[Letters.createdAt]
                    )
                }
        }
    }

    override fun fetchLettersForPlayer(player: Player, filterUnread: Boolean): List<Letter> {
        return transaction {
            Letters
                .select { Letters.toName.eq(player.name) and Letters.unread.eq(true) }
                .map {
                    Letter(
                        it[Letters.fromUuid],
                        it[Letters.fromName],
                        it[Letters.toUuid],
                        it[Letters.toName],
                        it[Letters.title],
                        Gson().fromJson<List<String>>(it[Letters.pages], List::class.java),
                        it[Letters.createdAt]
                    )
                }
        }
    }
}
