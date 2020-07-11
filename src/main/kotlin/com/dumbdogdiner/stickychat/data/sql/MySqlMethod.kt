package com.dumbdogdiner.stickychat.data.sql

class MySqlMethod(
    override val driver: String = "com.mysql.jdbc.Driver",
    override val protocol: String = "jdbc:mysql"
) : SqlMethod()
