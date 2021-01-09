package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.Formatter
import com.dumbdogdiner.stickychat.api.misc.SignNotification
import com.dumbdogdiner.stickychat.api.util.Placeholders
import com.dumbdogdiner.stickychat.api.util.StringModifier
import java.util.regex.Pattern
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
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
     * Formatting regex
     */
    private val colorFormattingRegex = Pattern.compile("(?<formatting>&(?:#[a-f0-9]{6}|[a-f0-9k-or]))?(?<content>.*?)(?=(&(?:#[a-f0-9]{6}|[a-f0-9k-or]))|\$)", Pattern.MULTILINE)

    /**
     * Format a chat message for this player. Fetches the chat format
     * from the cached configuration and interpolates placeholders as required.
     */
    override fun formatMessage(message: String): BaseComponent {
        val interp = StringModifier(this.config.getString("chat.format", "%player_name%: %message%"))
                .replace("%player_name%", this.player.name)
                .replace("%message%", message)
                .apply { Placeholders.setPlaceholdersSafe(this.player, it) }
                .get()

        val matcher = colorFormattingRegex.matcher(interp)

        var magic = false
        var bold = false
        var strike = false
        var underline = false
        var italic = false
        var color: ChatColor? = null

        val rootComponent = TextComponent()
        var nextComponent = TextComponent()

        while (matcher.find()) {
            if (matcher.group("formatting") != null) {
                val format = matcher.group("formatting")
                if (format[1] == '#') {
                    color = ChatColor.of(format.drop(1))
                } else {
                    when (format[1]) {
                        'x', 'X' -> magic = true
                        'l', 'L' -> bold = true
                        'm', 'M' -> strike = true
                        'n', 'N' -> underline = true
                        'o', 'O' -> italic = true
                        'r', 'R' -> {
                            magic = false
                            bold = false
                            strike = false
                            underline = false
                            italic = false
                            color = null
                        }
                        else -> color = ChatColor.getByChar(format[1])
                    }
                }
            }

            if (matcher.group("content") == null) {
                continue
            }

            nextComponent.text = matcher.group("content")
            nextComponent.isObfuscated = magic
            nextComponent.isBold = bold
            nextComponent.isStrikethrough = strike
            nextComponent.isUnderlined = underline
            nextComponent.isItalic = italic
            nextComponent.color = color
            rootComponent.addExtra(nextComponent)

            // reset component
            nextComponent = TextComponent()
            magic = false
            bold = false
            strike = false
            underline = false
            italic = false
        }

        return rootComponent
    }

    /**
     * Format a staff chat message for this player. Fetches the SC format
     * from the cached configuration and interpolates placeholders as required.
     */
    override fun formatStaffChatMessage(message: String): TextComponent {
        return TextComponent("[SC] ${player.name}: $message")
    }

    override fun formatOutgoingDM(to: Player, message: String): TextComponent {
        return TextComponent(
            StringModifier(this.config.getString("chat.outgoing.format", "&8[&e&lPM&r&8] &a{from_name} &8» &r{message}")!!)
                .apply { Formatter.colorize(it) }
                .replace("{from_name}", this.player.name)
                .replace("{message}", message)
                .get()
        )
    }

    override fun formatIncomingDM(from: Player, message: String): TextComponent {
        return TextComponent(
            StringModifier(this.config.getString("chat.incoming.format", "&8[&e&lPM&r&8] &a{from_name} &8» &r{message}")!!)
                .apply { Formatter.colorize(it) }
                .replace("{from_name}", from.name)
                .replace("{message}", message)
                .get()
        )
    }

    override fun formatSignSpyNotification(notification: SignNotification): BaseComponent {
        return TextComponent()
    }
}
