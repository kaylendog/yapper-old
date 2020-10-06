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
    group = "com.dumbdogdiner"
    version = "2.0.0-alpha-rc4"

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "java")

    repositories {
        jcenter()
        mavenCentral()
    }
}

// this feels more hacky than necessary
rootProject.childProjects.forEach { _ ->
    getTasksByName("build", false).forEach {
        it.dependsOn("ktlintFormat", "ktlintKotlinScriptCheck")
    }
}
