package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.sql.PostgresMethod

class StorageManager : Base, StorageMethod {
    private val cache = PlayerCache()
    private var storageMethod: StorageMethod = FileStorage()

    override fun init() {
        val method = config.getString("storage-method", "yaml")

        storageMethod = when (method) {
            "postgresql" -> PostgresMethod()
            else -> FileStorage()
        }

        storageMethod.init()
    }

    override fun setFormat(group: String, format: String): Boolean {
        cache.setGroupFormat(group, format)
        return storageMethod.setFormat(group, format)
    }

    override fun getFormat(group: String): String {
        if (cache.hasGroupFormat(group)) {
            return cache.getGroupFormat(group)!!
        }

        return storageMethod.getFormat(group)
    }
}
