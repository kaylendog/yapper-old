repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    // jvm and kotlin dependancies
    implementation(kotlin("stdlib"))
    // implementation("com.google.common")

    compileOnly("com.google.guava:guava:29.0-jre")
}
