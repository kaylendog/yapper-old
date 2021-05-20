/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.integration

import com.dumbdogdiner.stickychat.api.integration.Integration
import com.dumbdogdiner.stickychat.api.integration.IntegrationManager
import org.bukkit.plugin.Plugin

class SkIntegrationManager : IntegrationManager {
    private val integrations = HashMap<Plugin, SkIntegration>()

    override fun getIntegration(plugin: Plugin): Integration {
        var integration = integrations[plugin]
        if (integration == null) {
            integration = SkIntegration(plugin)
            integrations[plugin] = integration
        }
        return integration
    }
}
