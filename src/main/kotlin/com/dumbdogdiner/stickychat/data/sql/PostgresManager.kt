package com.dumbdogdiner.stickychat.data.sql

class PostgresManager(
    override val driver: String = "org.postgresql.Driver",
    override val protocol: String = "jdbc:postgresql://"
) : SqlManager()
