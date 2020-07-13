package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils
import com.dumbdogdiner.stickychat.utils.StringUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * Command for managing plugin configuration.
 */
class ChatManagementCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(StringUtils.colorize("&6StickyChat &8- &bv${plugin.description.version}\n&bAuthors: &a${plugin.description.authors.joinToString("")}"))
            return true
        }

        return when (args[0]) {
            "reload" -> reload(sender)
            else -> invalidUsage(sender)
        }
    }

    /**
     * Reload the plugin config.
     */
    fun reload(sender: CommandSender): Boolean {
        plugin.reloadConfig()
        ServerUtils.sendColorizedMessage(sender, "&aConfiguration reloaded!")
        return true
    }

    fun invalidUsage(sender: CommandSender): Boolean {
        ServerUtils.sendColorizedMessage(sender, "&cInvalid command usage.")
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        return if (args.size == 1 && sender.hasPermission("stickychat.reload")) {
            mutableListOf("reload")
        } else {
            mutableListOf()
        }
    }
}
