package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.utils.StringUtils
import org.jetbrains.exposed.sql.Table

/**
 * Table for storing sent mail messages.
 */
object Nicknames : Table(name = StringUtils.formatTableName("nicknames")) {
    val id = integer("id").autoIncrement()

    val user = varchar("user", 36)
    val value = text("content")

    val old = bool("old")

    override val primaryKey = PrimaryKey(id)
}
