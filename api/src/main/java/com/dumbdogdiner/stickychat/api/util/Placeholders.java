package com.dumbdogdiner.stickychat.api.util;

import org.bukkit.entity.Player;

public class Placeholders {
    /**
     * Class name of the placeholder API class.
     */
    private static final String PAPI_CLASS = "me.clip.placeholderapi.PlaceholderAPI";

    /**
     * Test if the current server has PlaceholderAPI installed.
     * @return {@link Boolean}
     */
    public static Boolean hasPlaceholderApiEnabled() {
        try {
            Class.forName(PAPI_CLASS);
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
            return (String) Class.forName(PAPI_CLASS)
                .getMethod("setPlaceholders", Player.class, String.class)
                .invoke(Class.forName(PAPI_CLASS), player, content);
        } catch(Exception e) {

            return content;
        }
    }
}
