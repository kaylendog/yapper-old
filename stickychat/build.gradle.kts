import kr.entree.spigradle.kotlin.paper
import kr.entree.spigradle.kotlin.papermc

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("kr.entree.spigradle") version "2.2.3"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

repositories {
    papermc()
    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    // jvm and kotlin dependencies
    implementation(kotlin("stdlib"))
    implementation(project(":stickychat-api"))

    // shaded dependencies
    implementation("redis.clients", "jedis", "3.3.0")
    implementation("org.jetbrains.exposed", "exposed-core", "0.28.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.28.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.28.1")
    implementation("org.postgresql", "postgresql", "42.2.18")
    implementation("com.zaxxer", "HikariCP", "3.4.5")
    implementation("com.google.code.gson", "gson", "2.8.6")

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
        val pkg = "com.dumbdogdiner.stickychat.libs."
        relocate("com.zaxxer", "${pkg}com.zaxxer")
//        relocate("kotlin", "${pkg}kotlin")
        relocate("org.apache", "${pkg}org.apache")
        relocate("org.checkerframework", "${pkg}org.checkerframework")
        relocate("org.intellij", "${pkg}org.intellij")
//        relocate("org.jetbrains", "${pkg}org.jetbrains")
        relocate("org.postgresql", "${pkg}org.postgresql")
        relocate("org.slf4j", "${pkg}org.slf4j")
        relocate("redis", "${pkg}redis")
    }

    spigot {
        name = "StickyChat"
        authors = mutableListOf("SkyezerFox")
        apiVersion = "1.16"
        softDepends = mutableListOf("PlaceholderAPI")

        commands {
            create("stickychat") {
                aliases = mutableListOf("chat", "sc")
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
            create("staffchat") {
                aliases = mutableListOf("sc")
                usage = "/staffchat [message]"
            }
        }

        permissions {
            create("stickychat.message") {}
        }
    }
}
