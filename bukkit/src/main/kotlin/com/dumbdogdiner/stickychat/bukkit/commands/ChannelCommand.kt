package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class ChannelCommand : WithPlugin, TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size > 2) {
            return StickyChat.getService().channelManager.channels.map { it.name }.toMutableList()
        }
        return mutableListOf("")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            this.integration.sendSystemError(sender, "This command must be run as a player!")
            return true
        }

        if (args.isEmpty()) {
            val channel = StickyChat.getService().getMessageService(sender).channel
            this.integration.sendSystemMessage(sender, "Your current channel is: ${channel.name}")
            return true
        }

        val channel = StickyChat.getService().channelManager.channels.find { it.name == args[0] }
        if (channel == null) {
            this.integration.sendSystemError(sender, "Could not find channel ${args[0]}!")
            return true
        }

        StickyChat.getService().getMessageService(sender).moveChannel(channel)
        return true
    }
}
