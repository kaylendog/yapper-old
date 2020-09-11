package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils
import com.dumbdogdiner.stickychat.utils.SoundUtils
import com.dumbdogdiner.stickychat.utils.StringUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Send messages to a user.
 */
class MessageCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2) {
            ServerUtils.sendColorizedMessage(sender, "&cInvalid command usage - " + (plugin.getCommand("message")?.usage))
            return true
        }

        when (val player = server.onlinePlayers.find { it.name.equals(args[0], true)}) {
            null -> sendExternalMessage(sender, args[0], args.drop(1).joinToString(""))
            else -> sendLocalMessage(sender, player, args.drop(1).joinToString(""))
        }

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

    /**
     * Send a message to a user on the current server.
     */
    private fun sendLocalMessage(from: CommandSender, to: Player, content: String) {
        to.sendMessage("${from.name}: $content")
        ServerUtils.sendColorizedMessage(from, "&bSuccessfully sent a message to ${to.name}!")

        if (from is Player) {
            SoundUtils.info(from)
        }
        SoundUtils.info(to)
    }

    /**
     * Send a message to a user on another server.
     */
    private fun sendExternalMessage(from: CommandSender, to: String, content: String) {
        ServerUtils.sendColorizedMessage(from, StringUtils.colorize("&cPlayer is not online! Or, if they are, shout at me!"))
        ServerUtils.sendColorizedMessage(from, StringUtils.colorize("&bUse /mail while I implement support for cross-server messaging."))

        if (from is Player) {
            SoundUtils.error(from)
        }
    }
}
