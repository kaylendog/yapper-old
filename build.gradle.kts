import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
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
