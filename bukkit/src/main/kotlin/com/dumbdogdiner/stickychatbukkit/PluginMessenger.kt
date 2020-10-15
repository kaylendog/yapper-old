// package com.dumbdogdiner.stickychatbukkit
//
// import com.dumbdogdiner.stickychatcommon.Constants
// import com.dumbdogdiner.stickychatcommon.MessageHandler
// import com.dumbdogdiner.stickychatcommon.MessageType
// import com.google.common.io.ByteArrayDataInput
// import com.google.common.io.ByteArrayDataOutput
// import com.google.common.io.ByteStreams
// import org.bukkit.Bukkit
// import org.bukkit.entity.Player
// import org.bukkit.plugin.messaging.PluginMessageListener
//
// /**
// * Utility methods for plugin messaging.
// */
// object PluginMessenger : Base, PluginMessageListener, MessageHandler {
//
//    private const val CHANNEL_NAME = Constants.CHANNEL_NAME
//
//    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
//        System.out.println(channel + " " + player.name)
//
//        if (channel != CHANNEL_NAME) {
//            return
//        }
//
//        logger.info("[MSG] Received plugin message from proxy")
//        handlePacket(ByteStreams.newDataInput(message))
//    }
//
//    /**
//     * Message Event
//     * - UTF - Player UUID
//     * - UTF - Player name
//     * - UTF - Message content (pre-colorized)
//     */
//
//    override fun handleMessage(data: ByteArrayDataInput) {
//        if (!config.getBoolean("chat.cross-server-messaging", true)) {
//            return
//        }
//
//        chatManager.sendGlobalChatMessage(data.readUTF(), data.readUTF(), data.readUTF())
//    }
//
//    fun broadcastMessage(player: Player, message: String) {
//        val out = build(MessageType.MESSAGE)
//        out.writeUTF(player.uniqueId.toString())
//        out.writeUTF(player.name)
//        out.writeUTF(message)
//
//        sendTargetedPluginMessage(player, out)
//    }
//
//    /**
//     * Private Message Event
//     * - UTF - From player UUID
//     * - UTF - To player name
//     * - UTF - Message content
//     * - Int - Nonce
//     */
//
//    override fun handlePrivateMessage(data: ByteArrayDataInput) {
//        val fromUuid = data.readUTF()
//        val fromName = data.readUTF()
//        val to = data.readUTF()
//
//        val target = server.onlinePlayers.find { it.name == to } ?: return
//
//        // Todo: Sent private message content
//
//        val content = data.readUTF()
//        val nonce = data.readInt()
//
//        privateMessageManager.handleReceivedPrivateMessage(fromUuid, fromName, target, content)
//        broadcastPrivateMessageAck(target, nonce)
//    }
//
//    fun broadcastPrivateMessage(player: Player, target: String, message: String, nonce: Int) {
//        val out = build(MessageType.PRIVATE_MESSAGE)
//        out.writeUTF(player.uniqueId.toString())
//        out.writeUTF(target)
//        out.writeUTF(message)
//        out.writeInt(nonce)
//
//        sendTargetedPluginMessage(player, out)
//    }
//
//    override fun handlePrivateMessageAck(data: ByteArrayDataInput) {
//        val uuid = data.readUTF()
//        val nonce = data.readInt()
//        privateMessageManager.handleReceivedAck(nonce)
//    }
//
//    private fun broadcastPrivateMessageAck(player: Player, nonce: Int) {
//        val out = build(MessageType.PRIVATE_MESSAGE_ACK)
//        out.writeUTF(player.uniqueId.toString())
//        out.writeInt(nonce)
//        sendTargetedPluginMessage(player, out)
//    }
//
//    override fun handlePrivateMessageError(data: ByteArrayDataInput) {
//        val fromUuid = data.readUTF()
//        val nonce = data.readInt()
//        privateMessageManager.handleMessageError(fromUuid, nonce)
//    }
//
//    override fun handleMailReceive(data: ByteArrayDataInput) {
//        val fromUuid = data.readUTF()
//        val fromName = data.readUTF()
//        val to = data.readUTF()
//
//        val target = server.onlinePlayers.find { it.name == to } ?: return
//
//        val content = data.readUTF()
//        val createdAt = data.readLong()
//
//        mailManager.handleReceivedMailMessage(fromUuid, fromName, target, content, createdAt)
//    }
//
//    fun sendMail(target: Player, fromUuid: String, fromName: String, toName: String, content: String, createdAt: Long) {
//        val out = build(MessageType.MAIL)
//
//        out.writeUTF(fromUuid)
//        out.writeUTF(fromName)
//        out.writeUTF(toName)
//        out.writeUTF(content)
//        out.writeLong(createdAt)
//
//        sendTargetedPluginMessage(target, out)
//    }
//
//    /**
//     * Send a plugin message to Bungee.
//     */
//    override fun sendPluginMessage(data: ByteArrayDataOutput) {
//        sendTargetedPluginMessage(
//            Bukkit.getOnlinePlayers().first(), data
//        )
//    }
//
//    /**
//     * Send a plugin message to the player with the given uuid.
//     */
//    override fun sendTargetedPluginMessage(uuid: String, data: ByteArrayDataOutput) {
//        if (Bukkit.getOnlinePlayers().isEmpty()) {
//            return
//        }
//        sendTargetedPluginMessage(Bukkit.getOnlinePlayers().find { it.uniqueId.toString() == uuid }!!, data)
//    }
//
//    /**
//     * Send a targeted plugin message via the specified player.
//     */
//    fun sendTargetedPluginMessage(target: Player, data: ByteArrayDataOutput) {
//        target.sendPluginMessage(plugin, Constants.CHANNEL_NAME, data.toByteArray())
//    }
// }
