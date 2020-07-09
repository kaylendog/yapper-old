package com.dumbdogdiner.stickychat.permissions

import com.dumbdogdiner.stickychat.Base
import org.bukkit.entity.Player

/**
 * An interface providing structure for resolvers that wrap permission plugins e.g. LuckPerms
 */
interface PermissionsResolver : Base {
    fun getPlayerGroup(player: Player): String
}