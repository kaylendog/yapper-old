// package com.dumbdogdiner.stickychatbukkit.managers
//
// import com.dumbdogdiner.stickychatbukkit.Base
// import com.dumbdogdiner.stickychatbukkit.utils.FormatUtils.colorize
// import com.dumbdogdiner.stickychatbukkit.utils.Priority
// import com.dumbdogdiner.stickychatbukkit.utils.ServerUtils
// import com.dumbdogdiner.stickychatbukkit.utils.SoundUtils
// import kotlinx.coroutines.GlobalScope
// import kotlinx.coroutines.launch
// import net.md_5.bungee.api.chat.ClickEvent
// import net.md_5.bungee.api.chat.TextComponent
// import org.bukkit.Material
// import org.bukkit.NamespacedKey
// import org.bukkit.entity.Player
// import org.bukkit.inventory.ItemStack
// import org.bukkit.inventory.meta.BookMeta
// import org.bukkit.persistence.PersistentDataType
//
// /**
// * Manages the sending, receiving, and persistent storage of mail messages.
// */
// class MailManager : Base {
//    private val players = HashMap<Player, ItemStack>()
//    private val destinations = HashMap<Player, String>()
//
//    /**
//     * Check for new messages sent to the given player.
//     */
//    fun checkForMail(recipient: Player) {
//        logger.info("[mail] Checking for unread mail for '${recipient.name}' (${recipient.uniqueId})...")
//
//        GlobalScope.launch {
//            val letters = storageManager.fetchLettersForPlayer(recipient, true)
//            if (letters.isEmpty()) {
//                logger.info("[mail] No new mail.")
//                return@launch
//            }
//            chatManager.sendSystemMessage(recipient, "&bYou have &e${letters.size} &bnew letter${if (letters.size > 1) {"s"} else ""}!")
//            SoundUtils.quietSuccess(recipient)
//        }
//    }
//
//    /**
//     * Create a new letter, giving the author a book.
//     */
//    fun createNewLetter(from: Player, to: String) {
//        if (players.containsKey(from)) {
//            return
//        }
//
//        val book = ItemStack(Material.WRITABLE_BOOK, 1)
//
//        val meta = book.itemMeta!!
//        meta.persistentDataContainer.set(NamespacedKey(plugin, "letter"), PersistentDataType.BYTE, 1)
//        book.itemMeta = meta
//
//        from.inventory.addItem(book)
//        SoundUtils.info(from)
//        chatManager.sendSystemMessage(from, "&bCreating a new letter addressed to &e$to &b- sign the book to send the message!")
//
//        players[from] = book
//        destinations[from] = to
//    }
//
//    /**
//     * Write a letter to the database.
//     */
//    fun writeLetter(from: Player, book: BookMeta) {
//        if (book.author == null || book.title == null) {
//            return
//        }
//
//        if (book.persistentDataContainer.get(NamespacedKey(plugin, "letter"), PersistentDataType.BYTE) != (1).toByte()) {
//            return
//        }
//
//        storageManager.savePartialLetter(from, destinations[from]!!, book.title!!, book.pages, System.currentTimeMillis())
//
//        SoundUtils.success(from)
//        chatManager.sendSystemMessage(from, "&bYour letter to &e${destinations[from]} &bhas been sent!")
//
//        // remove book from inventory
//        players[from]?.let { from.inventory.remove(it) }
//
//        players.remove(from)
//        destinations.remove(from)
//    }
//
//    /**
//     * Fetch a player's mail.
//     */
//    fun readAllMail(to: Player, page: Int) {
//        logger.info("Fetching mail for player '${to.name}' (${to.uniqueId})...")
//
//        GlobalScope.launch {
//            val letters = storageManager.fetchLettersForPlayer(to, filterUnread = true)
//            letters.forEach {
//                to.inventory.addItem((it.asItem()))
//            }
//        }
//    }
//
//    fun readUnreadMail(to: Player, page: Int) {
//        GlobalScope.launch {
//            storageManager.fetchLettersForPlayer(to, true)
//        }
//    }
//
//    /**
//     * Deliver a message to a player on the server.
//     */
//    private fun sendLocalLetter(from: Player, to: Player, content: String, createdAt: Long) {
//        if (from == to) {
//            ServerUtils.sendColorizedMessage(from, "&cYou cannot send a letter to yourself!")
//            SoundUtils.error(from)
//            return
//        }
//
//        chatManager.sendMessage(to, Priority.DIRECT, createReceivedMailTextComponent(from.name))
//        SoundUtils.info(to)
//    }
//
//    /**
//     * Attempt to deliver a mail message to someone on the network.
//     */
//    private fun sendRemoteLetter(from: Player, to: String, content: String, createdAt: Long) {
//        messenger.sendMail(from, from.uniqueId.toString(), from.name, to, content, createdAt)
//    }
//
//    /**
//     * Handle a received mail message from the plugin messenger.
//     */
//    fun handleReceivedMailMessage(fromUuid: String, fromName: String, to: Player, content: String, createdAt: Long) {
//        if (config.getBoolean("mail.notify-on-arrival", true)) {
//            chatManager.sendSystemMessage(to, createReceivedMailTextComponent(fromName))
//            SoundUtils.quietSuccess(to)
//        }
//
//        // TODO hydrate
//    }
//
//    /**
//     * Create the text component used for notifying a player of a new letter.
//     */
//    private fun createReceivedMailTextComponent(fromName: String): TextComponent {
//        val component = TextComponent()
//        component.text = colorize("&bYou have received a new letter from &e$fromName&b!}")
//
//        val clickComponent = TextComponent()
//        clickComponent.text = colorize("&f&l[&aOPEN&l]")
//        clickComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mail open")
//
//        component.addExtra(clickComponent)
//
//        return component
//    }
// }
