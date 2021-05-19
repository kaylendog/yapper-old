import org.bukkit.plugin.java.JavaPlugin

/**
 * The integration for this plugin
 */
val JavaPlugin.chatIntegration
	get() = chat.getIntegration(this)
