/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.models

import org.jetbrains.exposed.sql.Table

/**
 * Model for storing information about player blocks.
 */
object Blocks : Table() {
    val id = Blocks.integer("id").autoIncrement()

    /**
     * The player who is blocking the target.
     */
    var player = Blocks.varchar("player", 36)

    /**
     * The target this player has blocked.
     */
    var target = Blocks.varchar("target", 36)

    /**
     * Whether or not this block is active.
     */
    var active = Blocks.bool("active")

    /**
     * When this block was created.
     */
    var createdAt = Blocks.long("created_at")

    /**
     * When this block was deactivated.
     */
    var deactivatedAt = Blocks.long("deactivated_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
