package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.util.NotificationType
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Manage nicknames of yourself and of other players.
 */
class NicknameCommand : WithPlugin, TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size < 2) {
            return mutableListOf("get", "set")
        }

        if (args.size == 2) {
            return Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
        }
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            this.integration.sendSystemMessage(sender, "Invalid arguments")
            SoundUtil.send(sender, NotificationType.ERROR)
            return true
        }

        when (args[0]) {
            "get" -> {
                if (args.get(1) == null) {
                    if (sender !is Player) {
                        this.integration.sendSystemMessage(sender, "Cannot get nickname for a non-player entity!")
                        return true
                    }

                    val nick = this.plugin.getNicknameService(sender).nickname
                    if (nick == null) {
                        this.integration.sendSystemMessage(sender, "You do not currently have a nickname")
                        SoundUtil.send(sender, NotificationType.QUIET)
                    } else {
                        this.integration.sendSystemMessage(sender, "Your nickname is $nick")
                        SoundUtil.send(sender, NotificationType.QUIET)
                    }
                    return true
                }

                val target = Bukkit.getOnlinePlayers().find { it.name == args[1] }
                if (target == null) {
                    this.integration.sendSystemMessage(sender, "Could not find the target player")
                    SoundUtil.send(sender, NotificationType.ERROR)
                    return true
                }
            }
            "set" -> {
                if (args.get(1) == null) {
                    this.integration.sendSystemMessage(sender, "Invalid arguments")
                    SoundUtil.send(sender, NotificationType.ERROR)
                }
            }
            else -> {
                this.integration.sendSystemMessage(sender, "Invalid arguments")
                SoundUtil.send(sender, NotificationType.ERROR)
            }
        }

        return true
    }
}
