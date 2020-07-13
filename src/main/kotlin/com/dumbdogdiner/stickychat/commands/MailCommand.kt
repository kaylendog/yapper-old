package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Send a private message to another player.
 */
class MailCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command may only be run as a player.")
            return true
        }

        if (args.size < 2) {
            ServerUtils.sendColorizedMessage(sender, "&cInvalid command usage - /mail <player> <message>")
        }

        val target = args[0]
        val content = args.drop(1).joinToString(" ")
        mailManager.sendMailMessage(sender, target, content)

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size < 2) {
            return server.onlinePlayers.map { it.name }.toMutableList()
        }
        return mutableListOf()
    }
}
