package com.dumbdogdiner.stickychat.api.player;



import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Manages the storing of player nicknames. */
public interface NicknameProvider {
    /**
     * Load the target player's nickname, if they have one.
     *
     * @param player The target player
     * @return True if the action was successful.
     */
    @NotNull
    Boolean loadNickname(@NotNull Player player);

    /**
     * Test if the target player has a nickname.
     *
     * @param player The target player
     * @return True if the player has a nickname.
     */
    @NotNull
    Boolean hasNickname(@NotNull Player player);

    /**
     * Fetch the nickname for the target player.
     *
     * @param player The target player
     * @return A {@link String} containing the player's nickname.
     */
    @Nullable
    String getNickname(@NotNull Player player);

    /**
     * Get the display name of the target player.
     *
     * @param player The target player
     * @return A {@link String} containing the player's displayname.
     */
    default @NotNull String getDisplayname(@NotNull Player player) {
        return this.hasNickname(player) ? this.getNickname(player) : player.getName();
    }

    /**
     * Set the player's nickname to the target value.
     *
     * @param player The target player
     * @param newNickname The new nickname
     * @return True if the update was successful.
     */
    @NotNull
    Boolean setNickname(@NotNull Player player, @Nullable String newNickname);

    /**
     * Clear the player's nickname.
     *
     * @param player The target player
     * @return True if the update was successful.
     */
    default @NotNull Boolean clearNickname(@NotNull Player player) {
        return this.setNickname(player, null);
    }
}
