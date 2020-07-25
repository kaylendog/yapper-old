package com.dumbdogdiner.stickychat.managers

import com.dumbdogdiner.stickychat.Base
import com.dumbdogdiner.stickychat.utils.FormatUtils
import com.dumbdogdiner.stickychat.utils.SoundUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent

/**
 * Class for dealing with Sign changes.
 */
class SignSpyManager : Listener, Base {
    private val disabledPlayers = mutableSetOf<Player>()

    private val placedSigns = mutableSetOf<Player>()

    private val vladdyNitpicksALotOhYesHeDoes = TextComponent()

    init {
        vladdyNitpicksALotOhYesHeDoes.text = FormatUtils.colorize(" &bor ")
    }

    /**
     * Handle the creation of a sign.
     */
    fun handleSignCreation(e: SignChangeEvent) {
        if (!config.getBoolean("sign-spy.enabled", true)) {
            return
        }

        val msg = "&a${e.player.name} &bplaced a sign at ${e.block.location.x}, ${e.block.location.y}, ${e.block.location.z}" + "\n &r&8- &r" + e.lines.filter { txt -> txt.isNotBlank() }.joinToString("\n &r&8- &r")

        val utilComponent = TextComponent()
        utilComponent.text = FormatUtils.colorize(config.getString("prefix", "&b&lStickyChat &r&8» &r")!!)

        val teleportComponent = TextComponent()
        teleportComponent.text = FormatUtils.colorize("&b[TELEPORT]")
        teleportComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp ${e.block.location.blockX} ${e.block.location.blockY} ${e.block.location.blockZ}")

        // Todo: Add permission workaround.
        val destroyComponent = TextComponent()
        destroyComponent.text = FormatUtils.colorize("&c[DESTROY]")
        destroyComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/setblock ${e.block.location.blockX} ${e.block.location.blockY} ${e.block.location.blockZ} air destroy")

        utilComponent.addExtra(teleportComponent)
        utilComponent.addExtra(vladdyNitpicksALotOhYesHeDoes)
        utilComponent.addExtra(destroyComponent)

        val enableSound = config.getBoolean("sign-spy.enable-sound", true)

        for (it in server.onlinePlayers) {
            if (!it.hasPermission("stickychat.signspy")) {
                continue
            }

            chatManager.sendSystemMessage(it, msg)
            chatManager.sendSystemMessage(it, utilComponent)

            if (enableSound) {
                SoundUtils.info(it)
            }
        }

        // Todo: Attach this to permission workaround.
        placedSigns.add(e.player)
        GlobalScope.launch {
            delay(60000)
            placedSigns.remove(e.player)
        }
    }

    /**
     * Disable SignSpy for the specified player.
     */
    fun disableSignSpy(player: Player) {
        disabledPlayers.add(player)
    }

    /**
     * Enable SignSpy for the specified player.
     */
    fun enableSignSpy(player: Player) {
        disabledPlayers.remove(player)
    }
}
