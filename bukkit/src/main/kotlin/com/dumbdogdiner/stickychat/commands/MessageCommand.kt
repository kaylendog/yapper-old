package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Send messages to a user.
 */
class MessageCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command may only be used by players.")
            return true
        }

        if (args.size < 2) {
            ServerUtils.sendColorizedMessage(sender, "&cInvalid command usage - " + (plugin.getCommand("message")?.usage))
            return true
        }

        privateMessageManager.sendPrivateMessage(sender, args[0], args.drop(1).joinToString(" "))
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size < 2) {
            return server.onlinePlayers.map { it.name }.toMutableList()
        }
        return mutableListOf()
    }
}
