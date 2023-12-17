plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
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
