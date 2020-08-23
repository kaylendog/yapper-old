package com.dumbdogdiner.stickychat.gui

import com.dumbdogdiner.stickychat.data.Letter
import kotlin.math.max
import me.lucko.helper.item.ItemStackBuilder
import me.lucko.helper.menu.Gui
import me.lucko.helper.menu.scheme.MenuPopulator
import me.lucko.helper.menu.scheme.MenuScheme
import org.bukkit.Material
import org.bukkit.entity.Player

class LetterGui(player: Player, letters: List<Letter>) : Gui(player, max(letters.size / 27, 1), "Mail for ${player.name}") {

    // the display book
    private val DISPLAY = MenuScheme().mask("000010000")

    // the keyboard buttons
    private val BUTTONS = MenuScheme()
        .mask("000000000")
        .mask("000000001")
        .mask("111111111")
        .mask("111111111")
        .mask("011111110")
        .mask("000010000")

    private val KEYS = "PQWERTYUIOASDFGHJKLZXCVBNM"

    private val message = StringBuilder()

    override fun redraw() {
        // perform initial setup.
        // perform initial setup.
        if (isFirstDraw) {
            // place the buttons
            val populator: MenuPopulator = BUTTONS.newPopulator(this)
            for (keyChar in KEYS.toCharArray()) {
                populator.accept(ItemStackBuilder.of(Material.CLAY_BALL)
                    .name("&f&l$keyChar")
                    .lore("")
                    .lore("&7Click to type this character")
                    .build {
                        message.append(keyChar)
                        redraw()
                    }
                )
            }

            // space key
            populator.accept(ItemStackBuilder.of(Material.CLAY_BALL)
                .name("&f&lSPACE")
                .lore("")
                .lore("&7Click to type this character")
                .build {
                    message.append(" ")
                    redraw()
                }
            )
        }

        // update the display every time the GUI is redrawn.
        DISPLAY.newPopulator(this).accept(
            ItemStackBuilder.of(Material.BOOK)
                .name("&f$message&7_")
                .lore("")
                .lore("&f> &7Use the buttons below to type your message.")
                .lore("&f> &7Hit ESC when you're done!")
                .buildItem().build()
        )
    }
}
