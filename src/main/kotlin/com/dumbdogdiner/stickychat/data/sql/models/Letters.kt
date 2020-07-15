package com.dumbdogdiner.stickychat.data.sql.models

import com.dumbdogdiner.stickychat.utils.FormatUtils
import org.jetbrains.exposed.sql.Table

/**
 * Table for storing sent mail messages.
 */
object Letters : Table(name = FormatUtils.formatTableName("letters")) {
    val id = integer("id").autoIncrement()

    val fromName = varchar("from",  16)
    val toName = varchar("to", 16)

    val content = text("content")

    val createdAt = long("createdAt")

    override val primaryKey = PrimaryKey(id)
}
