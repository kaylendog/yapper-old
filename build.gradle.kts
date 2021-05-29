import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    java
}

allprojects {
    group = "com.dumbdogdiner.stickychat"
    version = "3.0.4-hotfix"

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
