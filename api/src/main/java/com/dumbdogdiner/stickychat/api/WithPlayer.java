package com.dumbdogdiner.stickychat.api;

import com.dumbdogdiner.stickychat.api.chat.DirectMessageService;
import com.dumbdogdiner.stickychat.api.chat.MessageService;
import com.dumbdogdiner.stickychat.api.chat.StaffChatService;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Interface providing base access to other services for a target player.
 */
public interface WithPlayer {
    /**
     * Return the player this object belongs to.
     *
     * @return {@link Player}
     */
    @NotNull
    Player getPlayer();

    /**
     * Return the data service for the player this object belongs to.
     *
     * @return {@link DataService}
     */
    @NotNull
    default DataService getDataService() {
        return StickyChat.getService().getDataService(this.getPlayer());
    }

    /**
     * Return the message service for the player this object belongs to.
     *
     * @return {@link MessageService}
     */
    @NotNull
    default MessageService getMessageService() {
        return StickyChat.getService().getMessageService(this.getPlayer());
    }

    /**
     * Return the direct message service for the player this object belongs to.
     *
     * @return {@link DirectMessageService}
     */
    @NotNull
    default DirectMessageService getDirectMessageService() {
        return StickyChat.getService().getDirectMessageService(this.getPlayer());
    }

    /**
     * Return the staff chat service for the player this object belongs to.
     *
     * @return {@link StaffChatService}
     */
    @NotNull
    default StaffChatService getStaffChatService() {
        return StickyChat.getService().getStaffChatService(this.getPlayer());
    }

    /**
     * Return the formatter for the player this object belongs to.
     *
     * @return {@link Formatter}
     */
    @NotNull
    default Formatter getFormatter() {
        return StickyChat.getService().getFormatter(this.getPlayer());
    }
}
