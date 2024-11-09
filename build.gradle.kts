import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishBasePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.maven.publish.plugin) apply false
    alias(libs.plugins.dokka)
}

allprojects {
    group = "dev.schlaubi.lyrics"
    version = "2.5.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply<MavenPublishBasePlugin>()
    configure<MavenPublishBaseExtension> {
        coordinates(project.group as String, project.name, project.version as String)
        if (System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKey") != null) {
            signAllPublications()
        }

        pom {
            name = "Lyrics.k"
            description = "A lyrics finder written in Kotlin"
            inceptionYear = "2023"
            url = "https://github.com/DRSchlaubi/lyrics.kt"

            licenses {
                license {
                    name = "MIT License"
                    url = "https://opensource.org/license/mit/"
                    distribution = "https://github.com/DRSchlaubi/lyrics.kt/blob/master/LICENSE"
                }
            }

            developers {
                developer {
                    name = "Michael Rittmeister"
                    email = "michael@rittmeister.in"
                }
            }

            scm {
                url = "https://github.com/DRSchlaubi/lyrics.kt"
                connection = "scm:git:https://github.com/DRSchlaubi/lyrics.kt.git"
            }
        }
    }

    afterEvaluate {
        configure<KotlinTopLevelExtension> {
            jvmToolchain(17)
        }
    }
}
