package com.dumbdogdiner.stickychatbukkit.data

import com.dumbdogdiner.stickychatbukkit.Base
import org.bukkit.entity.Player

/**
 * Manages the storage of formatting data.
 */
interface StorageMethod : Base {
    /**
     * Initialize this manager.
     */
    fun init()

    /**
     * Fetch a player's nickname.
     */
    fun getPlayerNickname(player: Player): String?

    /**
     * Fetch a player's display name. Will return their username if no nickname is present.
     */
    fun getPlayerDisplayname(player: Player) = getPlayerNickname(player) ?: player.name

    /**
     * Set a player's nickname.
     */
    fun setPlayerNickname(player: Player, new: String?): Boolean

    /**
     * Clear a player's nickname.
     */
    fun clearPlayerNickname(player: Player): Boolean

    /**
     * Save a letter for which the UUID of the target player cannot be found - for use
     * when sending a letter.
     */
    fun savePartialLetter(from: Player, toName: String, title: String, pages: List<String>, createdAt: Long): Boolean

    /**
     * Hydrate all partial letters for the given player.
     */
    fun hydratePartialLetters(to: Player)

    /**
     * Fetch a mail message from disk.
     * Todo: Use unique ids?
     */
    fun getLetter(id: Int): Letter?

    /**
     * Fetch letters for a given player.
     */
    fun fetchLettersForPlayer(player: Player): List<Letter>

    /**
     * Fetch letters for a given player, with an option to fetch only unread.
     */
    fun fetchLettersForPlayer(player: Player, filterUnread: Boolean): List<Letter>
}
