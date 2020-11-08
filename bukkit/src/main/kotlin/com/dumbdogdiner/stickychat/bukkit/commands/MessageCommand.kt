package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.result.DirectMessageResult
import com.dumbdogdiner.stickychat.api.util.NotificationType
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class MessageCommand : WithPlugin, TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (sender !is Player) {
            return Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
        }

        if (args.size < 2) {
            return StickyChat.getService().getDirectMessageService(sender).messageablePlayers.map { it.name }.toMutableList()
        }

        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) {
            return false
        }

        val player = Bukkit.getOnlinePlayers().find { it.name.toLowerCase() == args[0].toLowerCase() }
        if (player == null) {
            this.integration.sendSystemMessage(sender, "Could not send message - player does not exist!")
            SoundUtil.send(sender, NotificationType.ERROR)
            return true
        }

        val message = args.drop(1).joinToString(" ")

        val result: DirectMessageResult
        result = if (sender !is Player) {
            StickyChat.getService().getDirectMessageService(player).sendSystemMessage(message)
        } else {
            StickyChat.getService().getDirectMessageService(sender).sendTo(player, message)
        }

        if (result != DirectMessageResult.OK) {
            return false
        }
        return true
    }
}