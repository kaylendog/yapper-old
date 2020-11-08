package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.Formatter
import com.dumbdogdiner.stickychat.api.misc.SignNotification
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.entity.Player

class StickyFormatter private constructor(private val player: Player) : WithPlugin, Formatter {
    companion object {
        private val formatters = HashMap<Player, StickyFormatter>()

        /**
         * Get the formatter for the target player.
         */
        fun get(player: Player): StickyFormatter {
            if (formatters.containsKey(player)) {
                return formatters[player]!!
            }
            val formatter = StickyFormatter(player)
            formatters[player] = formatter
            return formatter
        }

        private val linkRegex = Regex("https?://[-a-zA-Z0-9@:%._+~#=]{1,256}.(?:[-a-zA-Z0-9@:%._+~#=/?=%&]{3,})+")
    }

    override fun getPlayer(): Player {
        return this.player
    }

    /**
     * Format a chat message for this player. Fetches the chat format
     * from the cached configuration and interpolates placeholders as required.
     */
    override fun formatMessage(message: String): BaseComponent {
        val words = message.split(" ")
        val text = TextComponent()

        words.forEachIndexed { i, it ->
            val component = TextComponent(it)
            if (it.matches(linkRegex)) {
                component.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, it)
                component.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("Click to open link"))
            }
            // TODO: I don't like this, but it works
            if (i != words.size - 1) {
                component.text = component.text.trim() + " "
            }
            text.addExtra(component)
        }

        val root = TextComponent()
        root.addExtra("${this.nicknameService.displayname}: ")
        root.addExtra(text)
        return root
    }

    /**
     * Format a staff chat message for this player. Fetches the SC format
     * from the cached configuration and interpolates placeholders as required.
     */
    override fun formatStaffChatMessage(message: String): TextComponent {
        return TextComponent("[SC] ${player.name}: $message")
    }

    override fun formatOutgoingDM(to: Player, message: String): TextComponent {
        return TextComponent("[DM] ${this.player.name} -> ${to.name}: $message")
    }

    override fun formatIncomingDM(from: Player, message: String): TextComponent {
        return TextComponent("[DM] ${from.name} -> ${this.player.name}: $message")
    }

    override fun formatSignSpyNotification(notification: SignNotification): BaseComponent {
        return TextComponent()
    }
}
