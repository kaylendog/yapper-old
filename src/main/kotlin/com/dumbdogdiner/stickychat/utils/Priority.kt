package com.dumbdogdiner.stickychat.utils

/**
 * Enum containing possible message priorities. Users can choose the minimum priority
 * required for them to receive messages.
 */
enum class Priority {
    ALL,
    IMPORTANT,
    DIRECT,
    SYSTEM
}
