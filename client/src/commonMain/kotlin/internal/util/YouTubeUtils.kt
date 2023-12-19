package dev.schlaubi.lyrics.internal.util

import dev.schlaubi.lyrics.LyricsNotFoundException
import dev.schlaubi.lyrics.protocol.Lyrics
import dev.schlaubi.lyrics.protocol.TimedLyrics
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

internal val JsonObject.browseEndpoint: String?
    get() = getJsonObject("contents")
        ?.getJsonObject("singleColumnMusicWatchNextResultsRenderer")
        ?.getJsonObject("tabbedRenderer")
        ?.getJsonObject("watchNextTabbedResultsRenderer")
        ?.getJsonArray("tabs")
        ?.getJsonObject(1)
        ?.getJsonObject("tabRenderer")
        ?.getJsonObject("endpoint")
        ?.getJsonObject("browseEndpoint")
        ?.getString("browseId")

internal val JsonObject.lyricsData: JsonObject?
    get() = getJsonObject("contents")
        ?.getJsonObject("elementRenderer")
        ?.getJsonObject("newElement")
        ?.getJsonObject("type")
        ?.getJsonObject("componentType")
        ?.getJsonObject("model")
        ?.getJsonObject("timedLyricsModel")
        ?.getJsonObject("lyricsData")

internal fun JsonObject.getTracks(albumArt: List<Lyrics.Track.AlbumArt>): Lyrics.Track {
    val lockScreen = getJsonObject("lockScreen")?.getJsonObject("lockScreenRenderer")
        ?: notFound()

    val title = lockScreen.getRunningText("title") ?: notFound()
    val author = lockScreen.getRunningText("shortBylineText") ?: notFound()
    val album = lockScreen.getRunningText("albumText") ?: notFound()

    return Lyrics.Track(title, author, album, albumArt)
}

internal val JsonObject.source: String
    get() = getString("sourceMessage")?.substringAfter(": ") ?: notFound()

internal val JsonObject.musicDescriptionShelfRenderer
    get() = getJsonObject("contents")
        ?.getJsonObject("sectionListRenderer")
        ?.getJsonArray("contents")
        ?.getJsonObject(0)
        ?.getJsonObject("musicDescriptionShelfRenderer")

internal val JsonObject.thumbnails: JsonArray?
    get() = getJsonObject("contents")
        ?.getJsonObject("singleColumnMusicWatchNextResultsRenderer")
        ?.getJsonObject("tabbedRenderer")
        ?.getJsonObject("watchNextTabbedResultsRenderer")
        ?.getJsonArray("tabs")
        ?.getJsonObject(0)
        ?.getJsonObject("tabRenderer")
        ?.getJsonObject("content")
        ?.getJsonObject("musicQueueRenderer")
        ?.getJsonObject("content")
        ?.getJsonObject("playlistPanelRenderer")
        ?.getJsonArray("contents")
        ?.getJsonObject(0)
        ?.getJsonObject("playlistPanelVideoRenderer")
        ?.getJsonObject("thumbnail")
        ?.getJsonArray("thumbnails")

internal fun JsonObject.toAlbumArt(): Lyrics.Track.AlbumArt {
    val url = getString("url")!!
    val height = getInt("height")!!
    val width = getInt("width")!!

    return Lyrics.Track.AlbumArt(url, height, width)
}

internal val JsonObject.lines: List<TimedLyrics.Line>
    get() {
        val linesData = getJsonArray("timedLyricsData") ?: notFound()

        return linesData.map {
            val json = it.jsonObject
            val line = json.getString("lyricLine")!!
            val range = json.getJsonObject("cueRange")?.let { range ->
                val start = range.getLong("startTimeMilliseconds") ?: return@let null
                val end = range.getLong("endTimeMilliseconds") ?: return@let null
                start..end
            } ?: error("Could not calculate range")

            TimedLyrics.Line(line, range)
        }
    }

internal fun notFound(): Nothing = throw LyricsNotFoundException()
