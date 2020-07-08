package com.dumbdogdiner.stickychat.bungee.utils

import com.dumbdogdiner.stickychat.Constants
import com.dumbdogdiner.stickychat.bungee.Base

object ServerUtils : Base {
    fun log(message: String) = proxy.logger.info(StringUtils.colorize("${Constants.CONSOLE_PREFIX}$message"))
    fun log(message: Throwable) = proxy.logger.info(StringUtils.colorize("${Constants.ERROR_PREFIX}$message"))
}