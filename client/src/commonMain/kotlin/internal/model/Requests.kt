package dev.schlaubi.lyrics.internal.model

import kotlinx.serialization.Serializable

internal const val onlyTracksSearchParam = "EgWKAQIIAWoQEAMQBBAJEAoQBRAREBAQFQ%3D%3D"

@Serializable
internal data class NextRequest(
    val context: Context,
    val videoId: String
)

@Serializable
internal data class BrowseRequest(
    val context: Context,
    val browseId: String
)

@Serializable
internal data class SearchRequest(
    val context: Context,
    val query: String,
    val params: String
)
