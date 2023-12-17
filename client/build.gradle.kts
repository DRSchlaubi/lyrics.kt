import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.dokka)
}

kotlin {
    explicitApi()
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.protocol)
                api(libs.ktor.client.core)
                api(libs.ktor.client.resources)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        named("jvmMain") {
            dependencies {
                api(libs.ktor.client.okhttp)
            }
        }
        named("jvmTest") {
            dependencies {
                api(kotlin("test-junit5"))
            }
        }
    }
}

tasks {
    withType<KotlinJvmTest> {
        useJUnitPlatform()
    }
}

mavenPublishing {
    configure(KotlinMultiplatform(JavadocJar.Dokka("dokkaJavadoc")))
    publishToMavenCentral(SonatypeHost.S01, true)
}
