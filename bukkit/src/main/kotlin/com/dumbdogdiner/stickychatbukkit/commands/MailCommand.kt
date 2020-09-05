package com.dumbdogdiner.stickychatbukkit.commands

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.utils.ServerUtils
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Send a private message to another player.
 */
class MailCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command may only be run as a player.")
            return true
        }

        if (args.isEmpty()) {
            mailManager.readAllMail(sender, 1)
            return true
        }

        val target = args[0]
        mailManager.createNewLetter(sender, target)

        return true
    }
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size < 2) {
            return (server.onlinePlayers.map { it.name } + listOf("send", "read")).toMutableList()
        }
        return mutableListOf()
    }
}
