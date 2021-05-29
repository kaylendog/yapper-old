import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kr.entree.spigradle.kotlin.paper
import kr.entree.spigradle.kotlin.papermc
import kr.entree.spigradle.module.common.YamlGenerate

plugins {
    // kotlin jvm
    kotlin("jvm") version "1.5.0"
    // lombok + annotation processing
    id("io.freefair.lombok") version "6.0.0-m2"
    // spigradle for quick dep resolution
    id("kr.entree.spigradle") version "2.2.3"
    // spotless for formatting
    id("com.diffplug.spotless") version "5.12.5"
    java
}

group = "com.dumbdogdiner"

allprojects {
    // set the global version
    version = "4.0.0"

    repositories {
        jcenter()
        mavenCentral()
        papermc()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.diffplug.spotless")

    group = "com.dumbdogdiner.stickychat"

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains:annotations:16.0.2")
        compileOnly(paper())
    }

    // target jvm 14
    tasks.withType<JavaCompile> {
        targetCompatibility = JavaVersion.VERSION_14.toString()
        sourceCompatibility = JavaVersion.VERSION_14.toString()
    }

    // target jvm 14
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "14"
    }

    // disable yaml generation by default
    tasks.withType<YamlGenerate> {
        enabled = false
    }


    spotless {
        kotlin {
            ktlint()
            licenseHeaderFile(rootProject.file("LICENSE_HEADER"))
        }
        java {
            importOrder()
            removeUnusedImports()
            eclipse().configFile(rootProject.file("eclipse-ddd-modified.xml"))
            licenseHeaderFile(rootProject.file("LICENSE_HEADER"))
        }
    }
}
