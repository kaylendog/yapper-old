/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.api.util.NotificationType
import com.dumbdogdiner.stickychat.api.util.SoundUtil
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * Send information regarding the version of the plugin.
 */
class ChatCommand : WithPlugin, TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size < 2) {
            return mutableListOf("reload")
        }
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (args.getOrNull(0) == "reload") {
            if (!sender.hasPermission("stickychat.reload")) {
                this.integration.sendSystemError(sender, "You do not have permission to run this command!")
                return true
            }
            this.plugin.reloadConfig()
            this.integration.sendSystemMessage(sender, "Reloaded configuration!")
            SoundUtil.send(sender, NotificationType.INFO)
            return true
        }

        this.integration.sendSystemMessage(sender, "Running version &a&lv${this.plugin.description.version}")
        SoundUtil.send(sender, NotificationType.SUCCESS)
        return true
    }
}
