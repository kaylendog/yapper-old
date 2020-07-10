package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.Base
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * Send a private message to another player.
 */
class MailCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) {
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return if (args.size < 2) {
            server.onlinePlayers.map { it.name }.toMutableList()
        } else mutableListOf()
    }
}
