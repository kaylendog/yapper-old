rootProject.name = "StickyChat"

// declare subprojects
include("common")
include("bukkit")
include("bungee")

// project(":common").name = "StickyChatCommon"
project(":bukkit").name = "StickyChatBukkit"
project(":bungee").name = "StickyChatBungee"
