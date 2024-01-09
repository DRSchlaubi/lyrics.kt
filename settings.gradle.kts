plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.4.1")
    }
}

rootProject.name = "lyrics-kt"

include("protocol", "client", "lavaplayer", "lavalink")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
