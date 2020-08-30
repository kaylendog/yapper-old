import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
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
    version = "2.0.0-alpha-rc2"

    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "java")

    repositories {
        jcenter()
        mavenCentral()
    }
}

// this feels more hacky than necessary
rootProject.childProjects.forEach { _ ->
    getTasksByName("ktlintKotlinScriptCheck", false).forEach {
        it.dependsOn("ktlintFormat")
    }

    getTasksByName("build", false).forEach {
        it.dependsOn("ktlintKotlinScriptCheck", "shadowJar")
    }

    getTasksByName("shadowJar", false).forEach {
        (it as ShadowJar).archiveClassifier.set("")
    }
}
