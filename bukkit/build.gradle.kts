import kr.entree.spigradle.kotlin.spigot
import kr.entree.spigradle.kotlin.spigotmc

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("kr.entree.spigradle") version "2.2.3"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

version = "3.0.0"

repositories {
    spigotmc()
    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    // jvm and kotlin dependancies
    implementation(kotlin("stdlib"))
    implementation(project(":StickyChatAPI"))

    compileOnly(spigot("1.16.3-R0.1-SNAPSHOT"))

    compileOnly("me.clip:placeholderapi:2.10.9")
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    build {
        dependsOn.add("ktlintFormat")
    }

    shadowJar {
        archiveClassifier.set("")
    }

    spigot {
        name = "StickyChat"
        authors = mutableListOf("SkyezerFox")
        apiVersion = "1.16"
        softDepends = mutableListOf("PlaceholderAPI")

        commands {
            create("version") {
                aliases = mutableListOf("stickychat", "chat")
            }
            create("message") {
                aliases = mutableListOf("tell", "msg", "whisper")
            }
            create("reply") {
                aliases = mutableListOf("r")
            }
        }

        permissions {
            create("stickychat.message") {}
        }
    }
}
