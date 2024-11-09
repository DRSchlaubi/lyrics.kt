import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost

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

mavenPublishing {
    configure(KotlinJvm(JavadocJar.Dokka("dokkaGeneratePublicationHtml")))
    publishToMavenCentral(SonatypeHost.S01, true)
}
