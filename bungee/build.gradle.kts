plugins {
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

repositories {
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
}

dependencies {
    // jvm and kotlin dependancies
    implementation(kotlin("stdlib"))
    implementation(project(":common"))

    compileOnly("net.md-5:bungeecord-api:1.16-R0.4-SNAPSHOT")
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveClassifier.set("")
    }
}
