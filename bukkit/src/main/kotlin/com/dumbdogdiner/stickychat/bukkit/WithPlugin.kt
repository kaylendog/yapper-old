package com.dumbdogdiner.stickychat.bukkit

interface WithPlugin {
    val plugin
        get() = StickyChatPlugin.plugin

    val logger
        get() = this.plugin.logger

    val integration
        get() = this.plugin.integrationManager.getIntegration(this.plugin)
}
