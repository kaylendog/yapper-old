package com.dumbdogdiner.stickychat.data.sql.models

import com.dumbdogdiner.stickychat.utils.StringUtils
import org.jetbrains.exposed.sql.Table

/**
 * Table for storing sent mail messages.
 */
object MailMessages : Table(name = StringUtils.formatTableName("messages")) {
    val id = integer("id").autoIncrement()

    val from = varchar("from", 36)
    val to = varchar("to", 36)

    val content = text("content")

    override val primaryKey = PrimaryKey(id)
}
