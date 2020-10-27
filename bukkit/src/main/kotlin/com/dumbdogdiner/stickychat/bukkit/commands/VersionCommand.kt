package com.dumbdogdiner.stickychat.bukkit.commands

import com.dumbdogdiner.stickychat.api.StickyChat
import com.dumbdogdiner.stickychat.bukkit.StickyChatPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * Send information regarding the version of the plugin.
 */
class VersionCommand : TabExecutor {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return mutableListOf()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("--- StickyChat ---")
        sender.sendMessage("Plugin: v${StickyChatPlugin.plugin.description.version}")
        sender.sendMessage("API: v${StickyChat.getVersion()}")
        return true
    }
}
