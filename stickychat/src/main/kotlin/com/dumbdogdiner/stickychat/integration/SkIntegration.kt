/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.integration

import com.dumbdogdiner.stickychat.api.integration.Integration
import org.bukkit.plugin.Plugin

class SkIntegration(private var plugin: Plugin) : Integration {
    private var prefix = plugin.name

    override fun getPlugin(): Plugin {
        return this.plugin
    }

    override fun setPrefix(prefix: String): String {
        this.prefix = prefix
        return prefix
    }

    override fun getPrefix(): String {
        return this.prefix
    }
}
