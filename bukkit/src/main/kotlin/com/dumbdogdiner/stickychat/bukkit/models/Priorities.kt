package com.dumbdogdiner.stickychat.bukkit.models

import org.jetbrains.exposed.sql.Table

/**
 * Stores player priority data.
 */
object Priorities : Table() {
    val id = Priorities.integer("id").autoIncrement()

    /**
     * The player who owns this nickname.
     */
    var player = Priorities.varchar("player", 36)

    /**
     * The value of the nickname.
     */
    var priority = Priorities.integer("priority")

    /**
     * Whether or not this player's nickname is active.
     */
    var active = Priorities.bool("active")

    override val primaryKey = PrimaryKey(id)
}
