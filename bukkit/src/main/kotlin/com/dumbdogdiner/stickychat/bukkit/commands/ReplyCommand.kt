package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.result.DirectMessageResult
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
        if (result != DirectMessageResult.OK) {
            return false
        }
        return true
    }
}
