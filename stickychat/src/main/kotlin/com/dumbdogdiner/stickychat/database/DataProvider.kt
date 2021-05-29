/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.database

import com.dumbdogdiner.stickychat.database.model.Nickname
import java.util.UUID

interface DataProvider {
    /**
	 * Initialize this data provider.
	 */
    fun initialize()

    /**
	 * Get the target player's nickname.
	 */
    fun getNickname(uuid: UUID): Nickname?

    /**
	 * Set this player's nickname.
	 */
    fun setNickname(uuid: UUID, value: String?): Boolean
}
