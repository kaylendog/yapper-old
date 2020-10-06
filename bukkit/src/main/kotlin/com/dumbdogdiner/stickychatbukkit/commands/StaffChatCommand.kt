package com.dumbdogdiner.stickychatbukkit.commands

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils
import com.dumbdogdiner.stickychatbukkit.utils.ServerUtils
import com.dumbdogdiner.stickychatbukkit.utils.SoundUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * Command for managing plugin configuration.
 */
class StaffChatCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command may only be run as a player.")
            return true
        }

        if (staffChatManager.getStaffChatEnabled(sender)) {
            staffChatManager.disableStaffChat(sender)
            chatManager.sendSystemMessage(sender, "&bDisabled staff chat!")

        } else {
            staffChatManager.enableStaffChat(sender)
            chatManager.sendSystemMessage(sender, "&bEnabled staff chat!")
        }

        SoundUtils.info(sender)
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
