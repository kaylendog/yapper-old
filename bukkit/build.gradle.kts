plugins {
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

repositories {
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("http://nexus.okkero.com/repository/maven-releases/") }
    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    // jvm and kotlin dependancies
    implementation(kotlin("stdlib"))
    implementation(project(":common"))

    compileOnly("org.spigotmc:spigot-api:1.16.2-R0.1-SNAPSHOT")

    implementation("org.jetbrains.exposed:exposed-core:0.26.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.26.1")
    implementation("org.postgresql:postgresql:42.2.2")
    implementation("mysql:mysql-connector-java:5.1.48")
    implementation("com.okkero.skedule:skedule:1.2.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("me.lucko:helper:5.6.5")

    compileOnly("net.luckperms:api:5.0")
    compileOnly("me.clip:placeholderapi:2.10.6")
}

tasks {
    build {
        dependsOn("shadowJar")
    }
    shadowJar {
        archiveClassifier.set("")
    }
}
