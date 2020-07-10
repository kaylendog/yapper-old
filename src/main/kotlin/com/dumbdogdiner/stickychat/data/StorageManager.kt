package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.sql.PostgresMethod
import org.bukkit.entity.Player

/**
 * Manages stored formats.
 */
class StorageManager : Base {
    private val defaultFormat = config.getString("chat.default-format", "&8%name%: %message%")!!

    private val cache = StorageCache()
    private var storageMethod: StorageMethod = FileStorage()

    fun init() {
        val method = config.getString("storage-method", "yaml")

        storageMethod = when (method) {
            "postgresql" -> PostgresMethod()
            else -> FileStorage()
        }

        storageMethod.init()
        storageMethod.getAllGroupFormats().forEach { cache.setGroupFormat(it.key, it.value) }
    }

    /**
     * Set the format of a group.
     */
    fun setFormat(group: String, format: String): Boolean {
        cache.setGroupFormat(group, format)
        return storageMethod.setGroupFormat(group, format)
    }

    /**
     * Get the format string for a particular player.
     */
    fun getPlayerFormat(player: Player): String {
        return getGroupFormat(permissionsResolver.getPlayerGroup(player))
    }

    fun getGroupFormat(group: String): String {
        if (cache.hasGroupFormat(group)) {
            return cache.getGroupFormat(group)!!
        }
        return storageMethod.getGroupFormat(group) ?: return defaultFormat
    }
}
