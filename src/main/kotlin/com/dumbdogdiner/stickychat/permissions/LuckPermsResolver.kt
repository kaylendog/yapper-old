package com.dumbdogdiner.stickychat.permissions

import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class LuckPermsResolver : PermissionsResolver {
    private val api: LuckPerms

    init {
        val provider = Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)!!
        api = provider.provider
    }

    /**
     * Get a list of a user's group.
     */
    override fun getPlayerGroup(player: Player): String {
        val user = api.userManager.getUser(player.uniqueId) ?: return "default"
        return user.primaryGroup
    }
}