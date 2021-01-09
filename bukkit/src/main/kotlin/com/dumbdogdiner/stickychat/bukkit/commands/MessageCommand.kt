package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class MessageCommand : WithPlugin, TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (sender !is Player) {
            return Bukkit.getOnlinePlayers().map { it.name }
        }

        if (args.size == 1) {
            return StickyChat
                .getService()
                .getDirectMessageService(sender).messageablePlayers
                    .map { it.name }
                    .filter { it.toLowerCase().startsWith(args[0]) }
        }

        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) {
            this.integration.sendSystemError(sender, "Invalid usage! /message <player> <message>")
            return true
        }

        val player = Bukkit.getOnlinePlayers().find { it.name.equals(args[0], ignoreCase = true) }
        if (player == null) {
            this.integration.sendSystemError(sender, "Could not send message - player does not exist!")
            return true
        }

        val message = args.drop(1).joinToString(" ")
        if (sender !is Player) {
            StickyChat.getService().getDirectMessageService(player).sendSystemMessage(message)
        } else {
            StickyChat.getService().getDirectMessageService(sender).sendTo(player, message)
        }

        return true
    }
}
