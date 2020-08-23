package com.dumbdogdiner.stickychatbukkit.commands

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils
import com.dumbdogdiner.stickychatbukkit.utils.ServerUtils
import com.dumbdogdiner.stickychatbukkit.utils.SoundUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * Command for managing plugin configuration.
 */
class ChatCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(FormatUtils.colorize("&bStickyChat &8- &ev${plugin.description.version}\n&bAuthors: &e${plugin.description.authors.joinToString("")}"))
            SoundUtils.safe(sender) { SoundUtils.quietSuccess(it) }
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

        ServerUtils.sendColorizedMessage(sender, "&eConfiguration reloaded!")
        SoundUtils.safe(sender) { SoundUtils.info(it) }

        return true
    }

    fun invalidUsage(sender: CommandSender): Boolean {
        ServerUtils.sendColorizedMessage(sender, "&cInvalid command usage.")
        SoundUtils.safe(sender) { SoundUtils.error(it) }
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
