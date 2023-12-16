package dev.schlaubi.lyrics.internal.model

import kotlinx.serialization.Serializable

internal val mobileYoutubeMusicContext = Context(Context.Client("ANDROID_MUSIC", "6.31.55"))

@Serializable
internal data class Context(
    val client: Client
) {
    @Serializable
    data class Client(val clientName: String, val clientVersion: String)
}
