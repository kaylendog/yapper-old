package com.dumbdogdiner.stickychatbukkit.commands

import com.dumbdogdiner.stickychatbukkit.utils.ServerUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class NickCommand : TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        ServerUtils.sendColorizedMessage(sender, "&cThis command is still being implemented!")
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
