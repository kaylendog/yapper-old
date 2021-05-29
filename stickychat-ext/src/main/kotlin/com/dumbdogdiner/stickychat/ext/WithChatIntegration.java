/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
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
