/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.listener

import com.dumbdogdiner.stickychat.WithSkChat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class ReloadListener : Listener, WithSkChat {
    @EventHandler
    fun onPlayerCommandPreprocess(e: PlayerCommandPreprocessEvent) {
        if (!e.message.startsWith("/reload")) {
            return
        }
        // warning message about /reload - does not work with command api
        this.instance.logger.warning("I detected you are performing a server reload via /reload!")
        this.instance.logger.warning("StickyChat will not behave as intended after reload - if you are reloading to")
        this.instance.logger.warning("update StickyChat, *everything will break!* Please perform a full server restart")
        this.instance.logger.warning("to correctly re-enable chat.")
    }
}
