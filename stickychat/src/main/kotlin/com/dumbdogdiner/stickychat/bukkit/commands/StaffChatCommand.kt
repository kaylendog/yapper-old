package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.util.SoundUtil
import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class StaffChatCommand : WithPlugin, TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            this.integration.sendSystemError(sender, "Only players can use staff chat!")
            return true
        }

        if (!sender.hasPermission("stickychat.staff-chat")) {
            this.integration.sendSystemError(sender, "You do not have permission for this command!")
            return true
        }

        val sc = this.plugin.getStaffChatService(sender)

        // toggle chat on
        if (args.isEmpty()) {
            if (sc.hasStaffChatEnabled()) {
                sc.disableStaffChat()
                this.integration.sendSystemMessage(sender, "Disabled staff chat!")
            } else {
                sc.enableStaffChat()
                this.integration.sendSystemMessage(sender, "Enabled staff chat!")
            }
            SoundUtil.sendQuiet(sender)
            return true
        }

        // send command content
        sc.sendStaffChatMessage(args.joinToString(" "))
        return true
    }
}
