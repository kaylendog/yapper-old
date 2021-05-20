/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.ext

import org.bukkit.plugin.java.JavaPlugin

/**
 * The integration for this plugin
 */
val JavaPlugin.chatIntegration
    get() = chat.getIntegration(this)
