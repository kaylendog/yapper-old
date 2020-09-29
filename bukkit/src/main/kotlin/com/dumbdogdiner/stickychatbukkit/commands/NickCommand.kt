package com.dumbdogdiner.stickychatbukkit.commands

import com.dumbdogdiner.stickychatbukkit.Base
import com.dumbdogdiner.stickychatbukkit.utils.ServerUtils
import com.dumbdogdiner.stickychatbukkit.utils.SoundUtils
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class NickCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command can only be run as a player.")
            return true
        }

        if (args.size != 2) {
            val target = Bukkit.getPlayer(args[0])

            if (target != null) {
                val nickname = storageManager.getPlayerNickname(sender)
                if (nickname == null) {
                    chatManager.sendSystemMessage(sender, "&bPlayer '&e${target.name}' &bdoes not have a nickname.")
                    SoundUtils.info(sender)
                    return true
                }

                chatManager.sendSystemMessage(sender, "&e${target.name}&b's nickname is '&e$nickname&b'.")
                SoundUtils.info(sender)
            }

            if (args[0].length < 3 || args[0].length > 16) {
                chatManager.sendSystemMessage(sender, "&cInvalid nickname! Must be between 3 and 16 characters.")
                SoundUtils.error(sender)
                return true
            }

            if (args[0] == "disabled") {
                storageManager.setPlayerNickname(sender, null)
                chatManager.sendSystemMessage(sender, "&bSuccessfully removed your nickname.")
                SoundUtils.success(sender)
                return true
            }

            storageManager.setPlayerNickname(sender, args[0])
            chatManager.sendSystemMessage(sender, "&bSuccessfully updated your nickname.")
            SoundUtils.success(sender)

            return true
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size < 2) {
            return (mutableListOf("disabled") + Bukkit.getOnlinePlayers().toMutableList().map { it.name }).toMutableList()
        }
        return mutableListOf()
    }
}
