import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.nemerosa.versioning") version "2.8.2"
    kotlin("jvm") version "1.4.10"
    java
}

allprojects {
    version = versioning.info.full

    repositories {
        jcenter()
        mavenCentral()
    }
}

subprojects {
    group = "com.dumbdogdiner.stickychat"

    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        jcenter()
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        targetCompatibility = JavaVersion.VERSION_14.toString()
        sourceCompatibility = JavaVersion.VERSION_14.toString()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "14"
    }
}
