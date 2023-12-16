plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    explicitApi()

    jvm()
    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
            }
        }
    }
}
