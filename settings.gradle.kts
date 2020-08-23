rootProject.name = "StickyChat"

// declare subprojects
include("bukkit")
include("bungee")

project(":bukkit").name = "StickyChatBukkit"
project(":bungee").name = "StickyChatBungee"
