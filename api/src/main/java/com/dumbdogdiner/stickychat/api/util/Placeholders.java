package com.dumbdogdiner.stickychat.api.util;

import org.bukkit.entity.Player;

public class Placeholders {
    /**
     * Test if the current server has PlaceholderAPI installed.
     * @return {@link Boolean}
     */
    public static Boolean hasPlaceholderApiEnabled() {
        try {
            Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Set placeholders safely, failing silently if PlaceholderAPI is not found.
     * @param player The player who's placeholders are being evaluated
     * @param content The content to interpolate placeholders into
     * @return {@link String}
     */
    public static String setPlaceholdersSafe(Player player, String content) {
        try {
            return (String) Class.forName("me.clip.placeholderapi.PlaceholderAPI")
                .getMethod("setPlaceholders", Player.class, String.class)
                .invoke(Class.forName("me.clip.placeholderapi.PlaceholderAPI"), player, content);
        } catch(Exception e) {
            return content;
        }
    }
}
