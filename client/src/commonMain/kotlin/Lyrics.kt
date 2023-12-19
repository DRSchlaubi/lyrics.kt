package dev.schlaubi.lyrics

import dev.schlaubi.lyrics.internal.model.*
import dev.schlaubi.lyrics.internal.util.*
import dev.schlaubi.lyrics.protocol.Lyrics
import dev.schlaubi.lyrics.protocol.SearchTrack
import dev.schlaubi.lyrics.protocol.TextLyrics
import dev.schlaubi.lyrics.protocol.TimedLyrics
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlin.coroutines.CoroutineContext

internal expect fun newClient(config: HttpClientConfig<*>.() -> Unit): HttpClient

/**
 * Client for retrieving lyrics from the YouTube API.
 */
public class LyricsClient : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
    private val client = newClient {
        install(ContentNegotiation) {
            json()
        }
        install(Resources)
        defaultRequest {
            url.takeFrom("https://music.youtube.com/")
        }
    }

    /**
     * Requests the [Lyrics] for [videoId].
     */
    public suspend fun requestLyrics(videoId: String): Lyrics {
        val nextPage = requestNextPage(videoId)
        val browseId = nextPage.browseEndpoint ?: throw LyricsNotFoundException()

        val browseResult = request(MusicApi.Browse(), BrowseRequest(mobileYoutubeMusicContext, browseId))

        val lyricsData = browseResult.lyricsData
        val albumArt = nextPage.thumbnails?.map { it.jsonObject.toAlbumArt() } ?: emptyList()
        return if (lyricsData != null) {
            val source = lyricsData.source
            TimedLyrics(nextPage.getTracks(albumArt), source, lyricsData.lines)
        } else {
            val renderer = browseResult.musicDescriptionShelfRenderer ?: notFound()
            val text = renderer.getRunningText("description")!!
            val source = renderer.getRunningText("footer")!!
            TextLyrics(nextPage.getTracks(albumArt), source, text)
        }
    }

    /**
     * Searches for [query].
     *
     * @see SearchTrack
     */
    public suspend fun search(query: String): List<SearchTrack> {
        val result = request(MusicApi.Search(), SearchRequest(mobileYoutubeMusicContext, query))

        return result
            .getJsonObject("contents")
            ?.getJsonObject("tabbedSearchResultsRenderer")
            ?.getJsonArray("tabs")
            ?.getJsonObject(0)
            ?.getJsonObject("tabRenderer")
            ?.getJsonObject("content")
            ?.getJsonObject("sectionListRenderer")
            ?.getJsonArray("contents")
            ?.firstOrNull {
                it is JsonObject && it.getJsonObject("musicShelfRenderer")?.getRunningText("title") == "Songs"
            }
            ?.jsonObject
            ?.getJsonObject("musicShelfRenderer")
            ?.getJsonArray("contents")
            ?.map { item ->
                val renderer = item.jsonObject
                    .getJsonObject("musicTwoColumnItemRenderer") ?: error("")
                val title = renderer.getRunningText("title")!!
                val videoId =
                    renderer.getJsonObject("navigationEndpoint")
                        ?.getJsonObject("watchEndpoint")
                        ?.getString("videoId")!!

                SearchTrack(videoId, title)
            } ?: emptyList()
    }

    override fun close() {
        client.close()
        cancel()
    }

    private suspend inline fun <reified T : Any, reified B : Any> request(resource: T, body: B) =
        client.post(resource) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body<JsonObject>()

    private suspend fun requestNextPage(videoId: String) =
        request(MusicApi.Next(), NextRequest(mobileYoutubeMusicContext, videoId))
}
