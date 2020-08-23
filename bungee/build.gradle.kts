
import kr.entree.spigradle.kotlin.bungeecord
import kr.entree.spigradle.kotlin.sonatype

plugins {
    id("kr.entree.spigradle.bungee") version "2.1.2"
}

repositories {
    sonatype()
}

dependencies {
    // jvm and kotlin dependancies
    implementation(kotlin("stdlib"))

    compileOnly(bungeecord())
}

tasks {
    bungee {
        description = "Proxy support for StickyChat"
        author = "SkyezerFox"
    }
}
