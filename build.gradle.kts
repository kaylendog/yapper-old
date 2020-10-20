plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    java
}

allprojects {
    repositories {
        jcenter()
        mavenCentral()
    }
}

subprojects {
    group = "com.dumbdogdiner.stickychat"
    version = "3.0.0"

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "java")

    repositories {
        jcenter()
        mavenCentral()
    }
}
