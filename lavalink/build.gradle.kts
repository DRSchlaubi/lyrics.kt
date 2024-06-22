import proguard.gradle.ProGuardTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.lavalink)
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation(projects.client)
    implementation(libs.ktor.client.java)
    implementation(projects.protocol)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.resources)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(projects.lavaplayer) {
        isTransitive = false
    }
}

lavalinkPlugin {
    name = "lyrics"
    apiVersion = libs.versions.lavalink.api
    serverVersion = libs.versions.lavalink.server
    path = "dev.schlaubi.lyrics.lavalink"
    configurePublishing = true
}

val proguard by tasks.registering(ProGuardTask::class)

publishing {
    repositories {
        maven("https://maven.lavalink.dev/releases") {
            credentials {
                username = System.getenv("LAVALINK_MAVEN_USER")
                password = System.getenv("LAVALINK_MAVEN_PASSWORD")
            }
        }
    }
}
