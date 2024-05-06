import proguard.gradle.ProGuardTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.lavalink)
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation(projects.client) {
        exclude(group = "io.ktor", module = "ktor-client-okhttp")
    }
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.java)
    implementation(projects.protocol)
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

tasks {
    jar {
        exclude("org/intellij/**", "org/jetbrains/**", "org/slf4j/**", "kotlin/**", "kotlinx/serialization/**")
        exclude("**/DebugProbesKt.bin")
        exclude("**/_COROUTINE")
    }
}

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
