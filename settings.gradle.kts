rootProject.name = "StickyChat"

// declare subprojects
include("api")
include("bukkit")

// project(":common").name = "StickyChatCommon"
project(":bukkit").name = "StickyChat"
project(":api").name = "StickyChatAPI"
