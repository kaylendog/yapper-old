/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.api.messenger;

import org.jetbrains.annotations.NotNull;

/** Represents a remote server. */
public interface RemoteServer {
    /**
     * Return the name of this server.
     *
     * @return A {@link String} containing the server's name.
     */
    @NotNull
    String getName();
}
