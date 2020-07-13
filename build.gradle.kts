
import kr.entree.spigradle.kotlin.paper

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"

    id("kr.entree.spigradle") version "2.0.1"
    id("com.github.johnrengelman.shadow") version "5.2.0"

    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

group = "com.dumbdogdiner"
version = "2.0.0"

repositories {
    mavenCentral()

    maven { url = uri("http://nexus.okkero.com/repository/maven-releases/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
}

dependencies {
    implementation(kotlin("stdlib"))

    compileOnly(paper("1.16.1"))

    implementation("org.jetbrains.exposed:exposed-core:0.26.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.26.1")
    implementation("org.postgresql:postgresql:42.2.2")
    implementation("mysql:mysql-connector-java:5.1.48")

    implementation("com.okkero.skedule:skedule:1.2.6")

    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    compileOnly("net.luckperms:api:5.0")
    compileOnly("me.clip:placeholderapi:2.10.6")
}

tasks {
    ktlintKotlinScriptCheck {
        dependsOn("ktlintFormat")
    }

    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveClassifier.set("")
    }

    spigot {
        authors = listOf("SkyezerFox")
        softDepends = listOf("PlaceholderAPI", "LuckPerms")

        commands {
            create("mail") {
                description = "Send a mail to another player on the network."
                usage = "/mail <player> <message>"
            }
            create("message") {
                description = "Send a private message to a player currently online."
                usage = "/message [player] <message>"
                aliases = listOf("msg", "r")
            }
            create("chat") {
                description = "StickyChat management and configuration."
            }
            create("testpluginmsg") {
                usage = "/testpluginmsg <content>"
            }
        }
        apiVersion = "1.16"
    }
}
