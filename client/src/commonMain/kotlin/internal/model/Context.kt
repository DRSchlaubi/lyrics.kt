package dev.schlaubi.lyrics.internal.model

import kotlinx.serialization.Serializable

private val youtubeMusicVersion = "7.11.50"

internal val mobileYoutubeMusicContext = Context(Context.Client("ANDROID_MUSIC", youtubeMusicVersion))
internal fun mobileYoutubeMusicContext(region: String?) =
    Context(Context.Client("ANDROID_MUSIC", youtubeMusicVersion, region))

@Serializable
internal data class Context(
    val client: Client
) {
    @Serializable
    data class Client(val clientName: String, val clientVersion: String, val hl: String? = null)
}
