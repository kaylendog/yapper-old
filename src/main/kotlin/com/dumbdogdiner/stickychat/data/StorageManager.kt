package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.h2.H2Method
import com.dumbdogdiner.stickychat.data.sql.MySqlMethod
import com.dumbdogdiner.stickychat.data.sql.PostgresMethod
import org.bukkit.entity.Player

/**
 * Manages stored formats.
 */
class StorageManager : Base {
    private val cache = StorageCache()
    private var storageMethod: StorageMethod = FileStorage()

    /**
     * Initialize the storage manager.
     */
    fun init() {
        val method = config.getString("storage-method", "yaml")

        storageMethod = when (method.orEmpty().toLowerCase()) {
            "postgresql" -> PostgresMethod()
            "h2" -> H2Method()
            "mysql" -> MySqlMethod()
            else -> {
                logger.info("No, or invalid storage method specified - using default FileStorage method")
                FileStorage()
            }
        }

        storageMethod.init()
    }

    /**
     * Save a mail message to local storage.
     */
    fun saveMailMessage(from: Player, to: String, content: String, createdAt: Long): Boolean {
        return storageMethod.saveMailMessage(from, to, content, createdAt)
    }
}
