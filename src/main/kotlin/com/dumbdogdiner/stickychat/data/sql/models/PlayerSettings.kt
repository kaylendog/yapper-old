package com.dumbdogdiner.stickychat.data.sql.models

import com.dumbdogdiner.stickychat.utils.FormatUtils
import org.jetbrains.exposed.sql.Table

/**
 * Stores player settings.
 */
object PlayerSettings : Table(FormatUtils.formatTableName("settings")) {
    val id = varchar("id", 36)
    val priority = short("priority")
    override val primaryKey = PrimaryKey(id)
}
