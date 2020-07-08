package com.dumbdogdiner.stickychat.spigot.commands

import com.dumbdogdiner.stickychat.spigot.utils.PluginMessenger
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class TestCommand : TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        PluginMessenger.sendPluginMessage()
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        TODO("Not yet implemented")
    }
}