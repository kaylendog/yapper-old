package com.dumbdogdiner.stickychat.bukkit

import com.dumbdogdiner.stickychat.api.messaging.Formatter
import com.dumbdogdiner.stickychat.api.signspy.SignNotification
import com.dumbdogdiner.stickychat.api.util.Placeholders
import com.dumbdogdiner.stickychat.api.util.StringModifier
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player

object SkFormatter : WithPlugin, Formatter {
    /**
     * Format a chat message for this player. Fetches the chat format
     * from the cached configuration and interpolates placeholders as required.
     */
    override fun formatMessage(player: Player, message: String): BaseComponent {
        val interp = StringModifier(this.config.getString("chat.format", "%player_name%: %message%"))
                .replace("%player_name%", player.name)
                .replace("%message%", message)
                .apply { Placeholders.setPlaceholdersSafe(player, it) }
                .get()

        return Formatter.formatHexCodes(interp)
    }

    /**
     * Format a staff chat message for this player. Fetches the SC format
     * from the cached configuration and interpolates placeholders as required.
     */
<<<<<<< HEAD
    override fun formatStaffChatMessage(message: String): TextComponent {
        val interp = StringModifier(this.config.getString("chat.staff-chat-format", "[SC] %player_name%: %message%"))
                .replace("%player_name%", this.player.name)
=======
    override fun formatStaffChatMessage(player: Player, message: String): TextComponent {
        val interp = StringModifier(this.config.getString("chat.staff-chat-format", "[SC] %player_name%: message"))
                .replace("%player_name%", player.name)
>>>>>>> d8d0ad7... v4 :sparkles: improvements to everything tbh
                .replace("%message%", message)
                .apply { Placeholders.setPlaceholdersSafe(player, it) }
                .get()
        return Formatter.formatHexCodes(interp)
    }

    override fun formatOutgoingDM(from: Player, to: Player, message: String): TextComponent {
        val interp = StringModifier(this.config.getString("dms.outgoing.format", "&8[&e&lPM&r&8] &a%from_name% &8» &r%message%")!!)
                .replace("%from_name%", from.name)
                .replace("%to_name%", to.name)
                .replace("%message%", message)
                .get()
        return Formatter.formatHexCodes(interp)
    }

    override fun formatIncomingDM(to: Player, from: Player, message: String): TextComponent {
        val interp = StringModifier(this.config.getString("dms.incoming.format", "&8[&e&lPM&r&8] &a%from_name% &8» &r%message%")!!)
                .replace("%from_name%", from.name)
                .replace("%to_name%", to.name)
                .replace("%message%", message)
                .get()
        return Formatter.formatHexCodes(interp)
    }

    override fun formatSignSpyNotification(notification: SignNotification): BaseComponent {
        return TextComponent()
    }
}
