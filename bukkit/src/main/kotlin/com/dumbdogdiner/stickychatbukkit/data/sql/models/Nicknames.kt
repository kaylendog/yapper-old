package com.dumbdogdiner.stickychatbukkit.data.sql.models

import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils
import org.jetbrains.exposed.sql.Table

/**
 * Table for player nicknames
 */
object Nicknames : Table(name = FormatUtils.formatTableName("nicknames")) {
    val id = varchar("id", 36)
    val value = text("value")
    override val primaryKey = PrimaryKey(id)
}
