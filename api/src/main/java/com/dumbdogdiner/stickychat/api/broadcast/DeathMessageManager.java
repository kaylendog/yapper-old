package com.dumbdogdiner.stickychat.api.broadcast;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Random;

/**
 * Handles the registration and distribution of death messages.
 */
public interface DeathMessageManager {
    /**
     * Fetch a HashMap of all death messages.
     *
     * @return {@link HashMap}
     */
    HashMap<EntityDamageEvent.DamageCause, String[]> getAllDeathMessages();

    /**
     * Get an array of death messages of a given type.
     *
     * @param cause The cause of death
     * @return {@link String[]}
     */
    default String[] getDeathMessagesOfType(EntityDamageEvent.DamageCause cause) {
        var res = this.getAllDeathMessages().get(cause);
        if (res == null) {
            return new String[] {};
        }
        return res;
    }

    /**
     * Get a random death message of the given type.
     *
     * @param cause The cause of death
     * @return {@link String}
     */
    default String getRandomOfType(EntityDamageEvent.DamageCause cause) {
        var messages = this.getDeathMessagesOfType(cause);
        int i = new Random().nextInt(messages.length);
        return messages[i];
    }

    /**
     * Get the death message for a given death event.
     *
     * @param ev The death event
     * @return {@link String}
     */
    default String getMessage(PlayerDeathEvent ev) {
        var damageEvent = ev.getEntity().getLastDamageCause();
        if (damageEvent == null) {
            throw new RuntimeException("Player died, but no last damage cause was found");
        }
        return getRandomOfType(damageEvent.getCause());
    }
}
