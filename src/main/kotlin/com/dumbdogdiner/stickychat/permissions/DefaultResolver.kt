package com.dumbdogdiner.stickychat.permissions

import org.bukkit.entity.Player

/**
 * Uses PostgreSQL for group definition and resolution.
 */
class DefaultResolver : PermissionsResolver {
    override fun getPlayerGroup(player: Player): String {
        TODO("Not yet implemented")
    }

    override fun getGroups(): Set<String> {
        TODO("Not yet implemented")
    }
}
