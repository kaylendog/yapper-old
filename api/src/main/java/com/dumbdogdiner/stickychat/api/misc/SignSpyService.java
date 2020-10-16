package com.dumbdogdiner.stickychat.api.misc;

import com.dumbdogdiner.stickychat.api.DataService;
import com.dumbdogdiner.stickychat.api.StickyChat;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages player spying on placed signs.
 */
public interface SignSpyService {
    @NotNull
    static List<Player> getSignSpyRecipients(@NotNull SignNotification notification) {
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
    @NotNull
    Player getPlayer();

    /**
     * Return the permission node of the plugin's SignSpy implementation.
     *
     * @return {@link String}
     */
    @NotNull
    String getPermissionNode();

    @NotNull
    default Boolean hasSignSpyPermission() {
        return this.getPlayer().hasPermission(this.getPermissionNode());
    }

    /**
     * Returns true if this player has SignSpy enabled.
     *
     * @return {@link Boolean}
     */
    @NotNull
    default Boolean hasSignSpyEnabled() {
        return StickyChat.getService().getDataService(this.getPlayer()).getSignSpyEnabled();
    }

    /**
     * Enable SignSpy for this player. This should call
     * {@link DataService#setSignSpyEnabled(Boolean)},
     * as well as inform the user of this action.
     *
     * @return {@link Boolean}
     */
    @NotNull
    Boolean enableSignSpy();

    /**
     * Disable SignSpy for this player.This should call
     * {@link DataService#setSignSpyEnabled(Boolean)},
     * as well as inform the user of this action.
     *
     * @return {@link Boolean}
     */
    @NotNull
    Boolean disableSignSpy();

    /**
     * Toggle SignSpy on this player. Returns true if the player now
     * has SignSpy enabled.
     *
     * @return {@link Boolean}
     */
    @NotNull
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
    void sendNotification(@NotNull SignNotification notification);
}
