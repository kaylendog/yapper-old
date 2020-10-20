package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.api.result.DirectMessageResult
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class ReplyCommand : TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }
        if (args.isEmpty()) {
            return false
        }
        val message = args.drop(1).joinToString(" ")
        val result = StickyChat.getService().getDirectMessageService(sender).sendToLast(message)
        if (result != DirectMessageResult.OK) {
            return false
        }
        return true
    }
}
