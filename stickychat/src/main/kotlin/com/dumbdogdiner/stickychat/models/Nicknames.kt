/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.models

import org.jetbrains.exposed.sql.Table

/**
 * Stores nicknames and nickname history for players.
 */
object Nicknames : Table() {
    val id = integer("id").autoIncrement()

    /**
     * The player who owns this nickname.
     */
    var player = varchar("player", 36)

    /**
     * The value of the nickname.
     */
    var nickname = text("nickname")

    /**
     * Whether or not this player's nickname is active.
     */
    var active = bool("active")

    override val primaryKey = PrimaryKey(id)
}
