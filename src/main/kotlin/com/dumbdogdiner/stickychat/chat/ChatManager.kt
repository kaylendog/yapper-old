package com.dumbdogdiner.stickychat.chat

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.GroupChatFormats
import com.dumbdogdiner.stickychat.utils.ServerUtils
import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Manages chat.
 */
class ChatManager : Base, Listener {
    var enabled = true

    val groupFormats = HashMap<String, String>()

    init {
        ServerUtils.log("Fetching stored group formats...")
        transaction {
            GroupChatFormats.selectAll().forEach {
                groupFormats[it[GroupChatFormats.key]] = it[GroupChatFormats.value]
            }
        }

        ServerUtils.log("Loaded ${groupFormats.size} group formats.")
    }

    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {

    }
}
