/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the GNU General Public License v3, see LICENSE for more information...
 */
package com.dumbdogdiner.stickychat.ext

import java.time.Instant

fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}

fun Instant.toLong(): Long {
    return this.toEpochMilli()
}
