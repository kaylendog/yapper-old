package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class ReplyCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command may only be used by players.")
            return true
        }

        if (args.isEmpty()) {
            ServerUtils.sendColorizedMessage(sender, "&cInvalid command usage - " + (plugin.getCommand("r")?.usage))
            return true
        }

        privateMessageManager.reply(sender, args.joinToString(" "))
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        return mutableListOf()
    }
}
