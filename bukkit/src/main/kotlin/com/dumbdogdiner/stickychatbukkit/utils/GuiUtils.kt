package com.dumbdogdiner.stickychatbukkit.utils

import me.lucko.helper.item.ItemStackBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

object GuiUtils {
    /**
     * Return an ItemStack with the head of the player with the given name.
     */
    fun getPlayerHead(player: Player): ItemStack {
        return ItemStackBuilder
            .of(Material.PISTON_HEAD)
            .amount(1)
            .transformMeta { (it as SkullMeta).owningPlayer = Bukkit.getOfflinePlayer(player.uniqueId) }
            .build()
    }
}
