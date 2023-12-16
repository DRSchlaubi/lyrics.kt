plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    maven("https://jitpack.io")
}

kotlin {
    explicitApi()
}

dependencies {
    api(projects.client)
    implementation(libs.lavaplayer)
}
