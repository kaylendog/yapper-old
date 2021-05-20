/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.messaging.DirectMessageResult
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Allow players to reply to direct messages without specifying
 * who they are replying to.
 */
class ReplyCommand : WithPlugin, TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            this.integration.sendSystemError(sender, "This command must be run as a player!")
            return true
        }
        if (args.isEmpty()) {
            this.integration.sendSystemError(sender, "Invalid syntax! /reply <message>")
            return true
        }
        val message = args.joinToString(" ")
        val result = StickyChat.getService().getDirectMessageService(sender).sendToLast(message)

        when (result) {
            DirectMessageResult.FAIL_NONEXISTENT -> {
                this.integration.sendSystemError(sender, "Could not send reply - no previous contact found!")
            }
            // this doesn't really make a lot of sense? honestly idk if this can even happen.
            DirectMessageResult.FAIL_BLOCK -> {
                this.integration.sendSystemError(sender, "Could not send reply - this user has blocked you!")
            }

            DirectMessageResult.FAIL_COOLDOWN -> {
                this.integration.sendSystemError(sender, "Could not send reply - you are on cooldown!")
            }

            DirectMessageResult.FAIL_MUTED -> {
                this.integration.sendSystemError(sender, "Could not send reply - you are muted!")
            }

            DirectMessageResult.FAIL_PRIORITY -> {
                this.integration.sendSystemError(sender, "Could not send reply - target has their chat priority set above direct messages!")
            }
            else -> {}
        }

        return true
    }
}
