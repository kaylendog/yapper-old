import kr.entree.spigradle.kotlin.bungeecord
import kr.entree.spigradle.kotlin.paper

plugins {
    kotlin("jvm") version "1.3.72"
    id("kr.entree.spigradle") version "2.0.1"
    id("kr.entree.spigradle.bungee") version "2.0.1"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.dumbdogdiner"
version = "1.0.0"


repositories {
    mavenCentral()

    maven {
        name = "okkero"
        url = uri("http://nexus.okkero.com/repository/maven-releases/")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(
        group = "org.jetbrains.kotlinx",
        name = "kotlinx-coroutines-core",
        version = "1.3.7"
    )

    compileOnly(paper("1.16.1"))
    compileOnly(bungeecord())

    implementation("com.okkero.skedule:skedule:1.2.6")
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        classifier = ""
    }

    spigot {
        main = "com.dumbdogdiner.stickychat.spigot.StickyChatPlugin"
        authors = listOf("SkyezerFox")
        commands {
            create("test") {
                description = "uwu"
            }
        }
        apiVersion = "1.16"
    }

    bungee {
        main = "com.dumbdogdiner.stickychat.bungee.StickyChatBungee"
        author = "SkyezerFox"
    }
}
