package com.dumbdogdiner.stickychatapi.misc;

import com.dumbdogdiner.stickychatapi.StickyChat;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages player spying on placed signs.
 */
public interface SignSpyService {
    static List<Player> getSignSpyRecipients(SignNotification notification) {
        var recipients = new ArrayList<Player>();
        StickyChat.getService().getDataServices().forEach((data) -> {
            if (data.getSignSpyEnabled()) {
                recipients.add(data.getPlayer());
            }
        });
        return recipients;
    }

    /**
     * Get the player this service refers to.
     *
     * @return {@link Player}
     */
    Player getPlayer();

    /**
     * Return the permission node of the plugin's SignSpy implementation.
     *
     * @return {@link String}
     */
    String getPermissionNode();

    default Boolean hasSignSpyPermission() {
        return this.getPlayer().hasPermission(this.getPermissionNode());
    }

    /**
     * Returns true if this player has SignSpy enabled.
     *
     * @return {@link Boolean}
     */
    default Boolean hasSignSpyEnabled() {
        return StickyChat.getService().getDataService(this.getPlayer()).getSignSpyEnabled();
    }

    /**
     * Enable SignSpy for this player. This should call
     * {@link com.dumbdogdiner.stickychatapi.DataService#setSignSpyEnabled(Boolean)},
     * as well as inform the user of this action.
     *
     * @return {@link Boolean}
     */
    Boolean enableSignSpy();

    /**
     * Disable SignSpy for this player.This should call
     * {@link com.dumbdogdiner.stickychatapi.DataService#setSignSpyEnabled(Boolean)},
     * as well as inform the user of this action.
     *
     * @return {@link Boolean}
     */
    Boolean disableSignSpy();

    /**
     * Toggle SignSpy on this player. Returns true if the player now
     * has SignSpy enabled.
     *
     * @return {@link Boolean}
     */
    default Boolean toggleSignSpy() {
        if (this.hasSignSpyEnabled()) {
            this.disableSignSpy();
            return false;
        }
        this.enableSignSpy();
        return true;
    };

    /**
     * Send a {@link SignNotification} to this player
     * .
     * @param notification The notification to send.
     */
    void sendNotification(SignNotification notification);
}
