package com.dumbdogdiner.stickychat.files

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.StringUtils

/**
 * Language utilities.
 */
object Language : Base {
    private const val path = "language"

    private fun get(path: String) = StringUtils.colorize(
        config.getString(path)!!
    )
    private fun getList(path: String) = config.getStringList(path).map {
        StringUtils.colorize(it)
    }

    val prefix
        get() = get("$path.prefix")

    val signSpySignCreated
        get() = "$prefix${get(
            "$path.sign-spy.sign-created"
        )}"
}
