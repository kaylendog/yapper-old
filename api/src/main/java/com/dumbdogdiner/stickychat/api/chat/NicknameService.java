package com.dumbdogdiner.stickychat.api.chat;

import com.dumbdogdiner.stickychat.api.util.WithPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Manages the getting and setting of nicknames. Implementations of this
 * service should cache nicknames locally, and only retrieve them at server
 * startup.
 */
public interface NicknameService extends WithPlayer {

    /**
     * Get this player's nickname.
     * @return {@link String}
     */
    @Nullable
    public String getNickname();

    /**
     * Set this player's nickname.
     * @param nickname The new nickname
     */
    public void setNickname(@NotNull String nickname);

    /**
     * Remove this player's nickname.
     */
    public void removeNickname();

    @NotNull
    public Boolean hasNickname();

    /**
     * Perform an initial load of this player's nickname.
     * Returns true if the load was successful.
     * @return {@link Boolean}
     */
    public Boolean loadNickname();

    /**
     * Get the display name for this player. If the player has a nickname,
     * this returns their nickname, else it returns their name.
     * @return {@link String}
     */
    @NotNull
    default String getDisplayname() {
        return this.hasNickname() ? Objects.requireNonNull(this.getNickname()) : this.getPlayer().getName();
    }
}
