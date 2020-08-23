package com.dumbdogdiner.stickychatbukkit.data

/**
 * Represents a sent mail message.
 */
class Letter(
    val fromUuid: String,
    val fromName: String,
    val toUuid: String?,
    val toName: String,
    val content: String,
    val createdAt: Long
)
