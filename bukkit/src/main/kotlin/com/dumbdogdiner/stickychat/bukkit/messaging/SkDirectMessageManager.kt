package com.dumbdogdiner.stickychat.bukkit.messaging

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageManager
import com.dumbdogdiner.stickychat.api.player.DirectMessageService
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageResult
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.entity.Player

class SkDirectMessageManager private constructor(private val player: Player) : WithPlugin, DirectMessageManager {
    override fun sendMessage(from: Player, to: Player, message: String): DirectMessageResult {
        TODO("Not yet implemented")
    }

    override fun getLast(player: Player): Player? {
        TODO("Not yet implemented")
    }

    override fun setLastPlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun sendToLast(player: Player?, message: String): DirectMessageResult {
        TODO("Not yet implemented")
    }

    override fun block(target: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun unblock(target: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun sendSystemMessage(player: Player, message: BaseComponent): DirectMessageResult {
        TODO("Not yet implemented")
    }

    override fun getDisabledPlayers(): MutableCollection<Player> {
        TODO("Not yet implemented")
    }

    override fun hasDirectMessagesDisabled(player: Player?): Boolean {
        TODO("Not yet implemented")
    }

    override fun enableDirectMessages(player: Player?) {
        TODO("Not yet implemented")
    }

    override fun disableDirectMessages(player: Player?) {
        TODO("Not yet implemented")
    }

}
