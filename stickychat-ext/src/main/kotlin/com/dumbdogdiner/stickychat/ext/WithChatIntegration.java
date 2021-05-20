package com.dumbdogdiner.stickychat.ext;

import com.dumbdogdiner.stickychat.api.StickyChat;
import com.dumbdogdiner.stickychat.api.integration.Integration;
import org.bukkit.plugin.Plugin;

/** An interface that provides quick access to StickyChat. */
public interface WithChatIntegration extends Plugin {
    /** @return The StickyChat API. */
    default StickyChat getChat() {
        return StickyChat.getService();
    }

    /** @return The integration for this plugin. */
    default Integration getIntegration() {
        return StickyChat.getService().getIntegration(this);
    }
}
