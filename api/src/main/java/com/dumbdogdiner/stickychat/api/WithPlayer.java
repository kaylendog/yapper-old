package com.dumbdogdiner.stickychat.api;

import com.dumbdogdiner.stickychat.api.chat.MessageService;
import com.dumbdogdiner.stickychat.api.chat.StaffChatService;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Interface providing base access to other services for a target player.
 */
public interface WithPlayer {
    @NotNull
    Player getPlayer();

    @NotNull
    default DataService getDataService() {
        return StickyChat.getService().getDataService(this.getPlayer());
    }

    @NotNull
    default MessageService getMessageService() {
        return StickyChat.getService().getMessageService(this.getPlayer());
    }

    @NotNull
    default Formatter getFormatter() {
        return StickyChat.getService().getFormatter(this.getPlayer());
    }

    @NotNull
    default StaffChatService getStaffChatService() {
        return StickyChat.getService().getStaffChatService(this.getPlayer());
    }
}
