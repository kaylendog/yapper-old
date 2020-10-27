plugins {
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

    repositories {
        jcenter()
        mavenCentral()
    }
}
