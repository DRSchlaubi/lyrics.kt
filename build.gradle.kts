import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
}
allprojects {
    group = "dev.schlaubi"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        configure<KotlinTopLevelExtension> {
            jvmToolchain(17)
        }
    }
}
