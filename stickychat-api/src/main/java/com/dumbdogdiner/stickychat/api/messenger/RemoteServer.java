package com.dumbdogdiner.stickychat.api.messenger;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a remote server.
 */
public interface RemoteServer {
    /**
     * Return the name of this server.
     * @return A {@link String} containing the server's name.
     */
    @NotNull String getName();
}
