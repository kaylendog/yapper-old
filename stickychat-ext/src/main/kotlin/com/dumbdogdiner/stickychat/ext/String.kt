/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.ext

import net.md_5.bungee.api.chat.TextComponent

/**
 * Quickly cast this string to a text component.
 */
fun String.asComponent(): TextComponent {
    return TextComponent(this)
}
