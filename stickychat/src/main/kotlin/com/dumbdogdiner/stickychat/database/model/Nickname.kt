/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.database.model

import java.time.Instant
import java.util.UUID
import org.jetbrains.exposed.sql.Table

class Nickname(
    val uuid: UUID,
    val active: Boolean,
    val createdAt: Instant,
    val deactivatedAt: Instant?
)

object Nicknames : Table("skc_nicknames") {
    val id = integer("id").autoIncrement()
    // the player this nickname is for
    val uuid = varchar("uuid", 36)
    // the value of this nickname
    val value = text("value")
    // is this nickname active
    var active = bool("active")
    // when this nickname was created
    val createdAt = long("created_at")
    // when this nickname was deactivated
    var deactivatedAt = long("deactivated_at").nullable()
}
