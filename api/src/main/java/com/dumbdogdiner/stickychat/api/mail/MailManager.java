package com.dumbdogdiner.stickychat.api.mail;

import com.dumbdogdiner.stickychat.api.result.MailResult;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Handles mail sending and receiving.
 */
public interface MailManager {
    /**
     * Return an array containing previous letters.
     * @return {@link java.util.List<Letter>}
     */
    default @NotNull List<Letter> getLetters(@NotNull Player player) {
        return this.getLetters(player,5);
    }

    /**
     * Return an array containing previous letters.
     * @param limit The maximum number of letters to retrieve.
     * @return {@link List<Letter>}
     */
    default @NotNull List<Letter> getLetters(@NotNull Player player, int limit) {
        return this.getLetters(player, 0, limit);
    }

    /**
     * Return an array containing previous letters.
     * @param page The page to select
     * @param limit The number of letters per page
     * @return {@link List<Letter>}
     */
    List<Letter> getLetters(@NotNull Player player, int page, int limit);

    /**
     * Send a letter to the target player.
     * @param from The player who sent the letter
     * @param to The player who will receive the letter
     * @return {@link MailResult}
     */
    MailResult sendLetter(@NotNull Player from, @NotNull Player to);
}
