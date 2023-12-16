plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "lyrics-kt"

include("protocol", "client", "lavaplayer", "lavalink")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
