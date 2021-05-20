import kr.entree.spigradle.kotlin.jitpack

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("kr.entree.spigradle")
}

repositories {
    // placeholder api repository
    maven {  url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    // codemc for nbt api - dep of command api
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
    // jitpack for command api
    jitpack()
}

dependencies {
    // plugin apis
    implementation(project(":stickychat-api"))
    implementation(project(":stickychat-ext"))

    // shaded dependencies
    implementation("redis.clients", "jedis", "3.3.0")
    implementation("org.jetbrains.exposed", "exposed-core", "0.28.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.28.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.28.1")
    implementation("org.postgresql", "postgresql", "42.2.18")
    implementation("com.zaxxer", "HikariCP", "3.4.5")
    implementation("com.google.code.gson", "gson", "2.8.6")

    // plugin dependencies dependencies
    compileOnly("me.clip:placeholderapi:2.10.9")
    // command api
    implementation("dev.jorel.CommandAPI:commandapi-shade:5.12")
    compileOnly("dev.jorel.CommandAPI:commandapi-core:5.12")
    compileOnly("dev.jorel.CommandAPI:commandapi-annotations:5.12")
    annotationProcessor("dev.jorel.CommandAPI:commandapi-annotations:5.12")
}

tasks {
    build {
        dependsOn("shadowJar")
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

        permissions {
            create("stickychat.message") {}
        }
    }
}
