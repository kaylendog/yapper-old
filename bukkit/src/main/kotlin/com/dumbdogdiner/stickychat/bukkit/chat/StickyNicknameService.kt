package com.dumbdogdiner.stickychat.bukkit.chat

import com.dumbdogdiner.stickychat.api.chat.NicknameService
import org.bukkit.entity.Player

class StickyNicknameService(private val player: Player) : NicknameService {
    private var cachedNickname: String? = null

    companion object {
        private val nicknameServices = HashMap<Player, NicknameService>()

        /**
         * Get the data service for the target player.
         */
        fun get(player: Player): NicknameService {
            if (nicknameServices.containsKey(player)) {
                return nicknameServices[player]!!
            }
            val nicknameService = StickyNicknameService(player)
            nicknameServices[player] = nicknameService
            return nicknameService
        }
    }

    override fun getPlayer(): Player {
        return this.player
    }

    override fun getNickname(): String? {
        return cachedNickname
    }

    override fun setNickname(nickname: String) {
        cachedNickname = nickname
    }

    override fun removeNickname() {
        cachedNickname = null
    }

    override fun hasNickname(): Boolean {
        return cachedNickname != null
    }
}
