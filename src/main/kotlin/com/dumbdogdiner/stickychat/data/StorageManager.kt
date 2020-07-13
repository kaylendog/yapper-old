package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.h2.H2Method
import com.dumbdogdiner.stickychat.data.sql.MySqlMethod
import com.dumbdogdiner.stickychat.data.sql.PostgresMethod

/**
 * Manages stored formats.
 */
class StorageManager : Base {
    private val cache = StorageCache()
    private var storageMethod: StorageMethod = FileStorage()

    fun init() {
        val method = config.getString("storage-method", "yaml")

        storageMethod = when (method.orEmpty().toLowerCase()) {
            "postgresql" -> PostgresMethod()
            "h2" -> H2Method()
            "mysql" -> MySqlMethod()
            else -> FileStorage()
        }

        storageMethod.init()
    }
}
