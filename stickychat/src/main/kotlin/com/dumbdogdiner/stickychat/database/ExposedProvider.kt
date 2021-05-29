/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.database

import com.dumbdogdiner.stickychat.WithSkChat
import com.dumbdogdiner.stickychat.database.model.Nickname
import com.dumbdogdiner.stickychat.database.model.Nicknames
import com.dumbdogdiner.stickychat.ext.toInstant
import com.dumbdogdiner.stickychat.ext.toLong
import com.dumbdogdiner.stickychat.ext.toUuid
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.time.Instant
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExposedProvider : DataProvider, WithSkChat {
    // configure database mode
    private fun configureMode() {
        when (val mode = this.instance.config.getString("database.mode", "postgresql")!!.lowercase()) {
            // set up postgresql config
            "postgresql", "postgres" -> this.configurePostgres()
            // set up mysql config
            "mysql", "msql" -> this.configureMsql()
            // set up h2 config
            "h2" -> this.configureH2()
            // unrecognized
            else -> {
                this.logger.warning("Unrecognized configuration option '$mode' - defaulting to H2")
                this.configureH2()
            }
        }
    }

    // configure postgres
    private fun configurePostgres() {
        // get host and port
        val host = this.instance.config.getString("database.host", "localhost")!!
        val port = this.instance.config.getInt("database.port", 5432)
        // get user and password
        val user = this.instance.config.getString("database.user", "stickychat")!!
        val password = this.instance.config.getString("database.password")
        val database = this.instance.config.getString("database.name", "stickychat")!!
        // auth warning
        if (password == null) {
            this.logger.warning("[SQL] Cannot authenticate with no password field - too insecure! Defaulting to H2")
            return this.configureH2()
        }
        // connect
        try {
            Database.connect("jdbc:pgsql://$host:$port/$database", driver = "org.postgresql.Driver", user, password)
        } catch (e: Exception) {
            this.logger.warning("[SQL] Failed to connect to database - defaulting to H2")
            e.printStackTrace()
            this.configureH2()
        }
    }

    // configure msql
    private fun configureMsql() {
        // get host and port
        val host = this.instance.config.getString("database.host", "localhost")!!
        val port = this.instance.config.getInt("database.port", 5432)
        // get user and password
        val user = this.instance.config.getString("database.user", "stickychat")!!
        val pword = this.instance.config.getString("database.password")
        val database = this.instance.config.getString("database.name", "stickychat")!!
        // auth warning
        if (pword == null) {
            this.logger.warning("[SQL] Cannot authenticate with no password field - too insecure! Defaulting to H2")
            return this.configureH2()
        }
        // configure hikari
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://$host:$port/$database"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = user
            password = pword
            maximumPoolSize = 10
        }
        // create datasource and connect
        try {
            val dataSource = HikariDataSource(config)
            Database.connect(dataSource)
        } catch (e: Exception) {
            this.logger.warning("[SQL] Failed to connect to database - defaulting to H2")
            e.printStackTrace()
            this.configureH2()
        }
    }

    // configure h2
    private fun configureH2() {
        // resolve relative h2 file
        val path = this.instance.config.getString("database.path", "./database.h2")!!
        val file = this.instance.dataFolder.resolve(path)
        // connect
        Database.connect("jdbc:h2:${file.absolutePath}", "org.h2.Driver")
    }

    override fun initialize() {
        // configure the database mode
        this.configureMode()
        this.logger.info("[SQL] Synchronizing table schemas...")
        SchemaUtils.create(Nicknames)
    }

    override fun getNickname(uuid: UUID): Nickname? {
        // find or return null
        val nick = Nicknames.select { Nicknames.uuid eq uuid.toString() }.singleOrNull() ?: return null
        // return the nickname object
        return Nickname(
            nick[Nicknames.uuid].toUuid(),
            nick[Nicknames.active],
            nick[Nicknames.createdAt].toInstant(),
            nick[Nicknames.deactivatedAt]?.toInstant()
        )
    }

    private fun clearActiveNickname(uuid: UUID) {
        transaction {
            Nicknames.update({ Nicknames.uuid eq uuid.toString() }) {
                it[active] = false
                it[deactivatedAt] = Instant.now().toLong()
            }
        }
    }

    override fun setNickname(uuid: UUID, value: String?): Boolean {
        this.clearActiveNickname(uuid)
        // if just clear - clear
        if (value == null) {
            return true
        }
        // insert
        transaction {
            Nicknames.insert {
                it[Nicknames.uuid] = uuid.toString()
                it[Nicknames.value] = value
                it[active] = true
                it[createdAt] = Instant.now().toLong()
            }
        }
        // return
        return true
    }
}
