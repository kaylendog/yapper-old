/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat

interface WithSkChat {
    /**
	 * Reference to the SkChat plugin instance.
	 */
    val instance: SkChat
        get() = SkChat.instance
}
