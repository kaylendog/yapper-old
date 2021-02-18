package com.dumbdogdiner.stickychat.bukkit.messaging

import com.dumbdogdiner.stickychat.api.Priority
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.event.DirectMessageEvent
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageManager
import com.dumbdogdiner.stickychat.api.player.DirectMessageService
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageResult
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.entity.Player

class SkDirectMessageManager private constructor(private val player: Player) : WithPlugin, DirectMessageManager {
    private val lastPlayers = hashMapOf<Player, Player>()

    override fun sendMessage(from: Player, to: Player, message: String): DirectMessageResult {
        val ev = DirectMessageEvent(from, to, message)
        this.callBukkitEvent(ev);
        if (ev.isCancelled) {
            return DirectMessageResult.withErrorMessage(ev.cancelReason);
        }
    }

    override fun getLast(player: Player): Player? {
        return lastPlayers[player]
    }

    override fun reply(player: Player, message: String): DirectMessageResult {
        if (!this.hasLastPlayer(player)) {
            return DirectMessageResult.FAIL_DISABLED
        }
        return this.sendMessage(player, this.getLast(player)!!, message)
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
