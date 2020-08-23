package com.dumbdogdiner.stickychat.data.sql

import com.dumbdogdiner.stickychat.Base
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs

/**
 * Wrapper class for logger.info
 */
class SqlLogger : Base, SqlLogger {
    override fun log(context: StatementContext, transaction: Transaction) {
        logger.info("[sql] debug: ${context.expandArgs(transaction)}")
    }
}
