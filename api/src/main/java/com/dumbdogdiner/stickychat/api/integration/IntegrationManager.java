package com.dumbdogdiner.stickychat.api.integration;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Manages integrations of StickyChat with other plugins.
 */
public interface IntegrationManager {
    /**
     * Get the integration for the target plugin.
     *
     * @param plugin The plugin who's integration is being retrieved
     * @return {@link Integration}
     */
    @NotNull
    Integration getIntegration(@NotNull Plugin plugin);
}
