package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs

/**
 * Wrapper class for ServerUtils.log
 */
class SqlLogger : SqlLogger {
    override fun log(context: StatementContext, transaction: Transaction) {
        ServerUtils.log("sql: ${context.expandArgs(transaction)}")
    }
}
