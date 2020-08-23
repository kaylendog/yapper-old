
import kr.entree.spigradle.kotlin.paper

plugins {
    id("kr.entree.spigradle") version "2.1.2"
}

repositories {
    maven { url = uri("http://nexus.okkero.com/repository/maven-releases/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
}

dependencies {
    implementation(kotlin("stdlib"))

    compileOnly(paper("1.16.2"))

    implementation("org.jetbrains.exposed:exposed-core:0.26.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.26.1")
    implementation("org.postgresql:postgresql:42.2.2")
    implementation("mysql:mysql-connector-java:5.1.48")
    implementation("me.lucko:helper:5.6.2")

    implementation("com.okkero.skedule:skedule:1.2.6")

    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    compileOnly("net.luckperms:api:5.0")
    compileOnly("me.clip:placeholderapi:2.10.6")
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    spigot {
        authors = listOf("SkyezerFox")
        softDepends = listOf("PlaceholderAPI")

        commands {
            create("mail") {
                description = "Send a mail to another player on the network."
                usage = "/mail [filter/player] [player] [unread]"
            }
            create("message") {
                description = "Send a private message to a player currently online."
                usage = "/message [player] <message>"
                aliases = listOf("msg", "tell", "whisper")
            }
            create("reply") {
                description = "Reply to the last private message you received."
                usage = "/reply <message>"
                aliases = listOf("r")
            }
            create("stickychat") {
                description = "StickyChat management and configuration."
                usage = "/stickychat [reload]"
                aliases = listOf("chat")
            }
        }

        // TODO permission nodes

        apiVersion = "1.16"
    }
}
