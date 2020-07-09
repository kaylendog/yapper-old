package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Manages the connection between PostgreSQL and the plugin.
 */
class SqlManager : Base {
    private val host: String
    private val port: Int
    private val database: String

    private val user: String
    private val password: String

    private lateinit var db: Database

    init {
        ServerUtils.log("Retrieving SQL config...")

        host = config.getString("postgres.host")!!
        port = config.getInt("postgres.port")

        database = config.getString("postgres.database")!!
        user = config.getString("postgres.username")!!
        password = config.getString("postgres.password")!!
    }

    /**
     * Initialize the database connection.
     */
    fun init() {
        try {
            ServerUtils.log("[sql] Setting up sql connection...")
            db = Database.connect(
                    "jdbc:postgresql://$host:$port/$database",
                    user = user,
                    password = password,
                    driver = "org.postgresql.Driver"
            )
            transaction {
                addLogger(SqlLogger())
                SchemaUtils.createMissingTablesAndColumns(MailMessages, Nicknames)
            }
            ServerUtils.log("[sql] Database ready.")
        } catch (err: Throwable) {
            ServerUtils.log(err)
            ServerUtils.log("[sql] Failed to connect to PostgreSQL - please check the plugin configuration.")
        }
    }
}
