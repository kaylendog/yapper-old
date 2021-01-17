package com.dumbdogdiner.stickychat.api.mail;

import com.dumbdogdiner.stickychat.api.result.MailResult;
import com.dumbdogdiner.stickychat.api.util.WithPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Handles mail sending and receiving.
 */
public interface MailService extends WithPlayer {
    /**
     * Return an array containing previous letters.
     * @return {@link ArrayList<Letter>}
     */
     public default ArrayList<Letter> getLetters() {
        return this.getLetters(5);
    }

    /**
     * Return an array containing previous letters.
     * @param limit The maximum number of letters to retrieve.
     * @return {@link ArrayList<Letter>}
     */
    public default ArrayList<Letter> getLetters(int limit) {
        return this.getLetters(0, limit);
    }

    /**
     * Return an array containing previous letters.
     * @param page The page to select
     * @param limit The number of letters per page
     * @return {@link ArrayList<Letter>}
     */
    public ArrayList<Letter> getLetters(int page, int limit);

    /**
     * Send a letter to the target player.
     * @param player The player who will receive the letter
     * @return {@link MailResult}
     */
    public MailResult sendLetter(@NotNull  Player player);

    /**
     * Handle receiving a letter.
     * @param letter The letter being received
     * @return {@link MailResult}
     */
    public MailResult receiveLetter(@NotNull Letter letter);
}
