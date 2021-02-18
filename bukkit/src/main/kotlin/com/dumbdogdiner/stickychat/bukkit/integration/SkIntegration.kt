package com.dumbdogdiner.stickychat.bukkit.integration

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
