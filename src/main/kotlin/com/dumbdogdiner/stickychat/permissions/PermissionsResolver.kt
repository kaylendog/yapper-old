package com.dumbdogdiner.stickychat.permissions

import com.dumbdogdiner.stickychat.Base
import org.bukkit.entity.Player

/**
 * An interface providing structure for resolvers that wrap permission plugins e.g. LuckPerms
 */
interface PermissionsResolver : Base {
    /**
     * Get the user's highest level group.
     */
    fun getPlayerGroup(player: Player): String

    /**
     * Get a list of all available groups.
     */
    fun getGroups(): Set<String>
}
