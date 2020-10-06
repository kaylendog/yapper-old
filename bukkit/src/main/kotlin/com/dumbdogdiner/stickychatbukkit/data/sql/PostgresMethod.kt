package com.dumbdogdiner.stickychatbukkit.data.sql

class PostgresMethod(
    override val driver: String = "org.postgresql.Driver",
    override val protocol: String = "jdbc:postgresql"
) : SqlMethod()
