import kr.entree.spigradle.kotlin.paper
import kr.entree.spigradle.kotlin.papermc

plugins {
	id("kr.entree.spigradle") version "2.2.3"
}

repositories {
	mavenCentral()
	papermc()
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation(project(":stickychat-api"))

	implementation(paper())
}

tasks {
	generateSpigotDescription {
		enabled = false
	}
}
