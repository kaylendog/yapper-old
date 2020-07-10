package com.dumbdogdiner.stickychat.data.sql

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.StorageMethod
import com.dumbdogdiner.stickychat.utils.ServerUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
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
        ServerUtils.log("[sql] Setting up sql connection...")
        db = Database.connect(
            "$protocol://$host:$port/$database",
            user = user,
            password = password,
            driver = driver
        )
        transaction {
            try {
                addLogger(SqlLogger())
                SchemaUtils.createMissingTablesAndColumns(
                    MailMessages, Nicknames,
                    Formats
                )
                ServerUtils.log("[sql] Database ready.")
            } catch (e: ExposedSQLException) {
                ServerUtils.log(e)
                ServerUtils.log("Failed to connect to server.")
                server.pluginManager.disablePlugin(plugin)
            }
        }
    }

    override fun getAllGroupFormats(): HashMap<String, String> {
        return transaction {
            val res = HashMap<String, String>()
            Formats.selectAll().forEach { res[it[Formats.key]] = it[Formats.value] }
            res
        }
    }

    // Asynchronously set group format - will be cached anyway.
    override fun setGroupFormat(group: String, format: String): Boolean {
        GlobalScope.launch {
            // Todo: Implement
            transaction { }
        }

        return true
    }

    override fun getGroupFormat(group: String): String? {
        return transaction {
            val res = Formats.select { Formats.key eq group }.singleOrNull() ?: return@transaction null
            return@transaction res[Formats.key]
        }
    }
}
