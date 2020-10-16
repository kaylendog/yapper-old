repositories {
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.3-R0.1-SNAPSHOT")
    implementation("org.jetbrains:annotations:16.0.2")
}
