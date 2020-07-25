package com.dumbdogdiner.stickychat.data

import org.bukkit.entity.Player

class FileStorage : StorageMethod {
    override fun init() {
        TODO("Not yet implemented")
    }

    override fun getPlayerNickname(player: Player): String? {
        TODO("Not yet implemented")
    }

    override fun setPlayerNickname(player: Player, new: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearPlayerNickname(player: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun saveLetter(from: Player, to: String, content: String, created: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLetter(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun fetchLettersForPlayer(player: Player): List<Letter> {
        TODO("Not yet implemented")
    }

    override fun fetchLettersForPlayer(player: Player, filterUnread: Boolean): List<Letter> {
        TODO("Not yet implemented")
    }
}
