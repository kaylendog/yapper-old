package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.Formatter
import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.broadcast.BroadcastManager
import com.dumbdogdiner.stickychat.api.broadcast.DeathMessageProvider
import com.dumbdogdiner.stickychat.api.channel.ChannelManager
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageManager
import com.dumbdogdiner.stickychat.api.messaging.StaffChatManager
import com.dumbdogdiner.stickychat.api.player.NicknameProvider
import com.dumbdogdiner.stickychat.api.player.PlayerBlockManager
import com.dumbdogdiner.stickychat.api.player.PriorityManager
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

object ApiProvider : WithPlugin, StickyChat {
    override fun getProvider(): Plugin {
        return this.plugin
    }

    override fun getDirectMessageManager(): DirectMessageManager {

    }

    override fun getStaffChatManager(): StaffChatManager {
        TODO("Not yet implemented")
    }

    override fun getNicknameManager(): NicknameProvider {
        TODO("Not yet implemented")
    }

    override fun getChannelManager(): ChannelManager {
        TODO("Not yet implemented")
    }

    override fun getFormatter(player: Player?): Formatter {
        TODO("Not yet implemented")
    }

    override fun getBroadcastService(): BroadcastManager {
        TODO("Not yet implemented")
    }

    override fun getDeathMessageManager(): DeathMessageProvider {
        TODO("Not yet implemented")
    }

    override fun getIntegrationManager(): IntegrationManager {
        TODO("Not yet implemented")
    }

    override fun disableChat(): Boolean {
        TODO("Not yet implemented")
    }

    override fun enableChat(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPriorityManager(): PriorityManager {
        TODO("Not yet implemented")
    }

    override fun getPlayerBlockManager(): PlayerBlockManager {
        TODO("Not yet implemented")
    }
}
