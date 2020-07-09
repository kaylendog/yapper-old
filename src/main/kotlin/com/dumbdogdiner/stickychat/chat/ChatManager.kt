package com.dumbdogdiner.stickychat.chat

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.data.sql.GroupChatFormats
import com.dumbdogdiner.stickychat.utils.ServerUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Manager for replacement of message content, formatting, etc.
 */
class ChatManager : Base, Listener {
    val groupFormats = HashMap<String, String>()

    init {
        ServerUtils.log("Fetching stored group formats...")
    }

    /**
     * Initialize the manager, fetching stored group formats.
     */
    fun init() {
        loadGroupFormats()
    }

    /**
     * Load all available formats.
     */
    fun loadGroupFormats() {
        groupFormats.clear()

        transaction {
            GroupChatFormats.selectAll().forEach {
                groupFormats[it[GroupChatFormats.key]] = it[GroupChatFormats.value]
            }
        }
        val registeredPermissionGroups = permissionsResolver.getGroups()

        ServerUtils.log("Loaded ${groupFormats.size} group out of ${registeredPermissionGroups.size} groups")

        // Warn about redundant formats
        val redundantFormats = mutableSetOf<String>()
        groupFormats.keys.forEach {
            if (!registeredPermissionGroups.contains(it)) {
                redundantFormats.add(it)
            }
        }

        if (redundantFormats.size != 0) {
            ServerUtils.log("Found ${redundantFormats.size} redundant formats")
            redundantFormats.map { ServerUtils.log(" - $it") }
        }
    }

    @EventHandler
    fun handleChatEvent(e: AsyncPlayerChatEvent) {
    }
}
