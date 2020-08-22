package com.dumbdogdiner.stickychat.commands

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Send a private message to another player.
 */
class MailCommand : Base, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ServerUtils.sendColorizedMessage(sender, "&cThis command may only be run as a player.")
            return true
        }

        ServerUtils.sendColorizedMessage(sender, "&cThis command has not yet been implemented!")
        return true

        if (args.isEmpty()) {
            mailManager.readAllMail(sender, 1)
            return true
        }

        val target = args[0]
        val content = args.drop(1).joinToString(" ")
        mailManager.sendMailMessage(sender, target, content)

        return true
    }

    /**
     * Send mail to a player.
     */
    fun sendMailToTarget(from: Player, to: Player, args: List<String>) {
        if (args.isEmpty()) {
            chatManager.sendSystemMessage(from, "&cInvalid command usage - /mail <player>")
            return
        }

        val target = args[0]
        val book = ItemStack(Material.WRITABLE_BOOK)
    }

    /**
     * Read mail sent to a player.
     */
    fun readPlayerMail(player: Player) {
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return mutableListOf()

        if (args.size < 2) {
            return (server.onlinePlayers.map { it.name } + listOf("send", "read")).toMutableList()
        }
        return mutableListOf()
    }
}
