/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.commands

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
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (args.size == 1) {
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .toMutableList()
                .plus("off")
                .filter { it.toLowerCase().startsWith(args[0].toLowerCase()) }
        }

        if (args.size == 2) {
            return mutableListOf("off").filter { it.toLowerCase().startsWith(args[1].toLowerCase()) }
        }

        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            this.integration.sendSystemError(sender, "This command must be run as a player!")
            return true
        }

        if (args.isEmpty()) {
            val nick = this.plugin.getNicknameService(sender).nickname
            if (nick == null) {
                this.integration.sendSystemMessage(sender, "You do not have a nickname set!")
            } else {
                this.integration.sendSystemMessage(sender, "Your nickname is &a$nick")
            }
            SoundUtil.send(sender, NotificationType.QUIET)
            return true
        }

        // get the target player's nickname.
        val target = Bukkit.getOnlinePlayers().find { it.name == args[0] }
        if (target != null) {
            if (args.size == 2) {
                val newNickname = args[1]
                if (newNickname == "off") {
                    this.plugin.getNicknameService(target).removeNickname()
                    this.integration.sendSystemMessage(sender, "Removed ${target.name}'s nickname")
                } else {
                    this.plugin.getNicknameService(target).setNickname(newNickname)
                    this.integration.sendSystemMessage(sender, "Set &a${target.name}&r's nickname to &a$newNickname")
                }
            } else {
                val nick = this.plugin.getNicknameService(target).nickname
                if (nick == null) {
                    this.integration.sendSystemMessage(sender, "&a${target.name}&r does not have a nickname set!")
                } else {
                    this.integration.sendSystemMessage(sender, "&a${target.name}&r's nickname is &a$nick")
                }
            }
            SoundUtil.send(sender, NotificationType.QUIET)
            return true
        }

        // set self nickname
        val newNickname = args[0]
        if (newNickname == "off") {
            this.plugin.getNicknameService(sender).removeNickname()
            this.integration.sendSystemMessage(sender, "Removed &a${sender.name}&r's nickname")
        } else {
            this.plugin.getNicknameService(sender).setNickname(newNickname)
            this.integration.sendSystemMessage(sender, "Set &a${sender.name}&r's nickname to &a$newNickname&r")
        }
        SoundUtil.send(sender, NotificationType.QUIET)
        return true
    }
}
