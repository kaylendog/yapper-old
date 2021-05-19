package com.dumbdogdiner.stickychat.bukkit.util

import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs

/**
 * SQL Logger for Exposed.
 */
class ExposedLogger : WithPlugin, SqlLogger {
    override fun log(context: StatementContext, transaction: Transaction) {
        this.logger.fine(context.expandArgs(transaction))
    }
}
