package com.dumbdogdiner.stickychatbukkit.commands

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.utils.ServerUtils
import com.dumbdogdiner.stickychatbukkit.utils.SoundUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Send a private message to another player.
 */
class MailCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        ServerUtils.sendColorizedMessage(sender, "&cThis command is unstable and is not fully ready for deployment yet.")
        SoundUtils.safe(sender) { SoundUtils.error(it) }
        return true

        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command may only be run as a player.")
            return true
        }

        // if no arguments
        if (args.isEmpty()) {
            mailManager.readAllMail(sender, 1)
            return true
        }

        // if too many arguments
        if (args.size > 1) {
            chatManager.sendSystemMessage(sender, "&cInvalid usage - /mail [player]")
            SoundUtils.error(sender)
            return true
        }

        val target = args[0]
        mailManager.createNewLetter(sender, target)

        return true
    }
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size < 2) {
            return (server.onlinePlayers.map { it.name }).toMutableList()
        }
        return mutableListOf()
    }
}
