package com.dumbdogdiner.stickychat.bukkit.integration

import com.dumbdogdiner.stickychat.api.integration.Integration
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager
import org.bukkit.plugin.Plugin

class StickyIntegrationManager : IntegrationManager {
    private val integrations = HashMap<Plugin, StickyIntegration>()

    override fun getIntegration(plugin: Plugin): Integration {
        var integration = integrations[plugin]
        if (integration == null) {
            integration = StickyIntegration(plugin)
            integrations[plugin] = integration
        }
        return integration
    }
}
