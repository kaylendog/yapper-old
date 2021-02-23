package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.broadcast.BroadcastManager
import com.dumbdogdiner.stickychat.api.broadcast.DeathMessageProvider
import com.dumbdogdiner.stickychat.api.channel.ChannelManager
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageManager
import com.dumbdogdiner.stickychat.api.messaging.Formatter
import com.dumbdogdiner.stickychat.api.player.NicknameProvider
import com.dumbdogdiner.stickychat.api.player.PlayerBlockManager
import com.dumbdogdiner.stickychat.api.player.PriorityManager
import org.bukkit.plugin.Plugin

object ApiProvider : WithPlugin, StickyChat {
    override fun getProvider(): Plugin {
        return this.plugin
    }

    override fun getDirectMessageManager(): DirectMessageManager {
        return this.plugin.directMessageManager
    }

    override fun getNicknameManager(): NicknameProvider {
        return this.plugin.nicknameProvider
    }

    override fun getChannelManager(): ChannelManager {
        return this.plugin.channelManager
    }

    override fun getFormatter(): Formatter {
        return SkFormatter
    }

    override fun getBroadcastService(): BroadcastManager {
        TODO("Not yet implemented")
    }

    override fun getDeathMessageProvider(): DeathMessageProvider {
        return this.plugin.deathMessageProvider
    }

    override fun getIntegrationManager(): IntegrationManager {
        return this.plugin.integrationManager
    }

    override fun getPriorityManager(): PriorityManager {
         return this.plugin.priorityManager
    }

    override fun getPlayerBlockManager(): PlayerBlockManager {
        return this.plugin.playerBlockManager
    }
}
