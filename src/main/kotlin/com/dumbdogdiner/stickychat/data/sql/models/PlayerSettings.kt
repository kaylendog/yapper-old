package com.dumbdogdiner.stickychat.data.sql.models

import com.dumbdogdiner.stickychat.utils.StringUtils
import org.jetbrains.exposed.sql.Table

/**
 * Stores player settings.
 */
object PlayerSettings : Table(StringUtils.formatTableName("settings")) {
    val id = varchar("id", 36)
    val priority = short("priority")
    override val primaryKey = PrimaryKey(id)
}
