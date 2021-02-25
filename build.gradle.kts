import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    java
}

allprojects {
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
