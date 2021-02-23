import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.nemerosa.versioning") version "2.14.0"
    kotlin("jvm") version "1.4.10"
    java
}

allprojects {
    version = versioning.info.full
    group = "com.dumbdogdiner.stickychat"

    repositories {
        jcenter()
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "kotlin")


    tasks.withType<JavaCompile> {
        targetCompatibility = JavaVersion.VERSION_14.toString()
        sourceCompatibility = JavaVersion.VERSION_14.toString()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "14"
    }
}
