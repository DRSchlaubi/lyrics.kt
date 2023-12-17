import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.dokka)
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

mavenPublishing {
    configure(KotlinMultiplatform(JavadocJar.Dokka("dokkaJavadoc")))
    publishToMavenCentral(SonatypeHost.S01, true)
}
