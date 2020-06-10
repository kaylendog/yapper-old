package com.dumbdogdiner.stickychat.utils

import org.bukkit.Material

/**
 * Utility methods for dealing with blocks and material types.
 */
object BlockUtils {
    /**
     * Is this block a sign? Idk, you tell me smh.
     */
    fun isSign(mat: Material): Boolean {
        return when (mat) {
            Material.ACACIA_SIGN -> true
            Material.ACACIA_WALL_SIGN -> true
            Material.BIRCH_SIGN -> true
            Material.BIRCH_WALL_SIGN -> true
            Material.DARK_OAK_SIGN -> true
            Material.DARK_OAK_WALL_SIGN -> true
            Material.JUNGLE_SIGN -> true
            Material.JUNGLE_WALL_SIGN -> true
            Material.OAK_SIGN -> true
            Material.OAK_WALL_SIGN -> true
            Material.SPRUCE_SIGN -> true
            Material.SPRUCE_WALL_SIGN -> true

            // Deprecated, but here for good measure.
            Material.LEGACY_SIGN_POST -> true
            Material.LEGACY_SIGN -> true
            Material.LEGACY_WALL_SIGN -> true

            else -> false
        }
    }
}   