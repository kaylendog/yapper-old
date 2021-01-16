import kr.entree.spigradle.kotlin.paper
import kr.entree.spigradle.kotlin.papermc

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("kr.entree.spigradle") version "2.2.3"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

version = "3.0.0"

repositories {
    papermc()
    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    // jvm and kotlin dependencies
    implementation(kotlin("stdlib"))
    implementation(project(":StickyChatAPI"))

    // shaded dependencies
    implementation("redis.clients:jedis:3.3.0")
    implementation("org.jetbrains.exposed:exposed-core:0.26.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.26.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.26.1")
    implementation("org.postgresql:postgresql:42.2.2")

    // server dependencies
    compileOnly(paper("1.16.4-R0.1-SNAPSHOT"))
    compileOnly("me.clip:placeholderapi:2.10.9")

    // testing dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("com.google.guava:guava:30.0-jre")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {

    check {
        dependsOn("ktlintFormat")
    }

    build {
        dependsOn("shadowJar")
    }

    test {
        dependsOn("compileTestKotlin")
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    shadowJar {
        archiveClassifier.set("")
        // someone please help i don't know why this doesn't work
        // relocate("kotlin", "com.dumbdogdiner.stickychat.libs.kotlin")
        // relocate("redis", "com.dumbdogdiner.stickychat.libs.redis")
        // relocate("org.postgresql", "com.dumbdogdiner.stickychat.libs.org.postgresql")
        // relocate("org.jetbrains", "com.dumbdogdiner.stickychat.libs.org.jetbrains")
        // relocate("org.apache", "com.dumbdogdiner.stickychat.libs.org.apache")
        // relocate("org.slf4j", "com.dumbdogdiner.stickychat.libs.org.slf4j")
    }

    spigot {
        name = "StickyChat"
        authors = mutableListOf("SkyezerFox")
        apiVersion = "1.16"
        softDepends = mutableListOf("PlaceholderAPI")

        commands {
            create("version") {
                aliases = mutableListOf("stickychat", "chat", "sc")
            }
            create("message") {
                aliases = mutableListOf("tell", "msg", "whisper")
                usage = "/message <player> <message>"
            }
            create("reply") {
                aliases = mutableListOf("r")
                usage = "/reply <message>"
            }
            create("nickname") {
                aliases = mutableListOf("nick")
                usage = "/nickname <get|set> [value|player] [nick]"
            }
            create("channel") {
                aliases = mutableListOf("ch")
                usage = "/channel [name]"
            }
        }

        permissions {
            create("stickychat.message") {}
        }
    }
}
