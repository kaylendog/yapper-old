package com.dumbdogdiner.stickychatcommon

/**
 * Enum of possible plugin messages sent between server nodes, and to the Bungee plugin.
 */
enum class MessageType {
    MESSAGE,
    PRIVATE_MESSAGE,
    PRIVATE_MESSAGE_ACK,
    MAIL
}
