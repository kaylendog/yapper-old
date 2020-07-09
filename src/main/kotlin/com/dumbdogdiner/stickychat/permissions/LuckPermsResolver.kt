package com.dumbdogdiner.stickychat.permissions

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Resolver for LuckPerms.
 */
class LuckPermsResolver : PermissionsResolver {
    private val api: LuckPerms

    init {
        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)!!
        api = provider.provider
    }

    override fun getPlayerGroup(player: Player): String {
        val user = api.userManager.getUser(player.uniqueId) ?: return "default"
        return user.primaryGroup
    }

    override fun getGroups(): Set<String> {
        return api.groupManager.loadedGroups.toList().map { it.name }.toSet()
    }

    companion object {
        /**
         * Return whether LuckPerms is supported.
         */
        fun isSupported() = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java) != null
    }
}
