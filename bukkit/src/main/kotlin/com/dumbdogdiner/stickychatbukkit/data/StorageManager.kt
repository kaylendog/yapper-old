package com.dumbdogdiner.stickychatbukkit.data

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.data.h2.H2Method
import com.dumbdogdiner.stickychatbukkit.data.sql.MySqlMethod
import com.dumbdogdiner.stickychatbukkit.data.sql.PostgresMethod
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bukkit.entity.Player

/**
 * Manages stored formats.
 * Todo: Async/synchronous filtering.
 */
class StorageManager : Base,
    StorageMethod {
    private val cache = StorageCache()
    private var storageMethod: StorageMethod =
        FileStorage()

    /**
     * Initialize the storage manager.
     */
    override fun init() {
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
     * Fetch a player displayname.
     */
    override fun getPlayerDisplayname(player: Player): String {
        return getPlayerNickname(player) ?: player.name
    }

    /**
     * Fetch a player's nickname.
     */
    override fun getPlayerNickname(player: Player): String? {
        logger.info("[nick] Fetching nickname for player '${player.name}' (${player.uniqueId})...")

        val cached = cache.getPlayerNickname(player)
        if (cached != null) {
            return cached
        }

        return storageMethod.getPlayerNickname(player)
    }

    /**
     * Set a player's nickname.
     */
    override fun setPlayerNickname(player: Player, new: String?): Boolean {
        cache.setPlayerNickname(player, new)
        GlobalScope.launch {
            storageMethod.setPlayerNickname(player, new)
        }
        return true
    }

    /**
     * Clear a player's nickname.
     */
    override fun clearPlayerNickname(player: Player): Boolean {
        cache.deleteNickname(player)
        GlobalScope.launch {
            storageMethod.clearPlayerNickname(player)
        }
        return true
    }

    override fun savePartialLetter(from: Player, toName: String, title: String, pages: List<String>, createdAt: Long): Boolean {
        logger.info("Saving letter from '${from.name}' (${from.uniqueId}) to player '$toName'")
        GlobalScope.launch {
            storageMethod.savePartialLetter(from, toName, title, pages, createdAt)
        }
        return true
    }

    override fun hydratePartialLetters(to: Player) {
        return storageMethod.hydratePartialLetters(to)
    }

    /**
     * Fetch a mail message from disk.
     * Todo: Use unique ids?
     */
    override fun getLetter(id: Int): Letter? {
        return storageMethod.getLetter(id)
    }

    /**
     * Fetch letters for a given player.
     */
    override fun fetchLettersForPlayer(player: Player): List<Letter> {
        return storageMethod.fetchLettersForPlayer(player)
    }

    /**
     * Fetch letters for a given player, with an option to fetch only unread.
     */
    override fun fetchLettersForPlayer(player: Player, filterUnread: Boolean): List<Letter> {
        return storageMethod.fetchLettersForPlayer(player, filterUnread)
    }
}
