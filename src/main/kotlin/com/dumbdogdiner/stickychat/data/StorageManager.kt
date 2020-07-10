package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.sql.PostgresMethod

/**
 * Manages stored formats.
 */
class StorageManager : Base {
    private val cache = StorageCache()
    private var storageMethod: StorageMethod = FileStorage()

    fun init() {
        val method = config.getString("storage-method", "yaml")

        storageMethod = when (method) {
            "postgresql" -> PostgresMethod()
            else -> FileStorage()
        }

        storageMethod.init()
    }
}
