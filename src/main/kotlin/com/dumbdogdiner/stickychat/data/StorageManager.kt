package com.dumbdogdiner.stickychat.data

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.h2.H2Method
import com.dumbdogdiner.stickychat.data.sql.MySqlMethod
import com.dumbdogdiner.stickychat.data.sql.PostgresMethod
import org.bukkit.entity.Player

/**
 * Manages stored formats.
 * Todo: Async/synchronous filtering.
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
     * Fetch a player's nickname.
     */
    fun getPlayerNickname(player: Player): String? {
        val cached = cache.getPlayerNickname(player)
        if (cached != null) {
            return cached
        }

        return storageMethod.getPlayerNickname(player) ?: player.name
    }

    /**
     * Fetch a player's display name. Will return their username if no nickname is present.
     */
    fun getPlayerDisplayname(player: Player) = getPlayerNickname(player) ?: player.name

    /**
     * Set a player's nickname.
     */
    fun setPlayerNickname(player: Player, new: String): Boolean {
        cache.setPlayerNickname(player, new)
        return storageMethod.setPlayerNickname(player, new)
    }

    /**
     * Clear a player's nickname.
     */
    fun clearPlayerNickname(player: Player): Boolean {
        cache.deleteNickname(player)
        return storageMethod.clearPlayerNickname(player)
    }

    /**
     * Store a mail message.
     */
    fun saveLetter(from: Player, to: String, content: String, created: Long): Boolean {
        return storageMethod.saveLetter(from, to, content, created)
    }

    /**
     * Fetch a mail message from disk.
     * Todo: Use unique ids?
     */
    fun getLetter(id: Int): Boolean {
        return storageMethod.getLetter(id)
    }

    /**
     * Fetch letters for a given player.
     */
    fun fetchLettersForPlayer(player: Player): List<Letter> {
        return storageMethod.fetchLettersForPlayer(player)
    }

    /**
     * Fetch letters for a given player, with an option to fetch only unread.
     */
    fun fetchLettersForPlayer(player: Player, filterUnread: Boolean): List<Letter> {
        return storageMethod.fetchLettersForPlayer(player, filterUnread)
    }
}
