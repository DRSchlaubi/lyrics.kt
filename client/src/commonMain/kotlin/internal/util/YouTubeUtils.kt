package dev.schlaubi.lyrics.internal.util

import dev.schlaubi.lyrics.protocol.Lyrics
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

internal val JsonObject.lyricsData: JsonObject
    get() = getJsonObject("contents")
        ?.getJsonObject("elementRenderer")
        ?.getJsonObject("newElement")
        ?.getJsonObject("type")
        ?.getJsonObject("componentType")
        ?.getJsonObject("model")
        ?.getJsonObject("timedLyricsModel")
        ?.getJsonObject("lyricsData") ?: notFound("lyricsData")

internal val JsonObject.track: Lyrics.Track
    get() {
        val lockScreen = getJsonObject("lockScreen")?.getJsonObject("lockScreenRenderer")
            ?: notFound("lockscreen")

        val title = lockScreen.getRunningText("title") ?: notFound("title")
        val author = lockScreen.getRunningText("shortBylineText") ?: notFound("shortBylineText")
        val album = lockScreen.getRunningText("albumText") ?: notFound("albumText")

        return Lyrics.Track(title, author, album)
    }

internal val JsonObject.source: String
    get() = getString("sourceMessage")?.substringAfter(": ") ?: notFound("sourceMessage")

internal val JsonObject.lines:List<Lyrics.Line>
    get() {
        val linesData = getJsonArray("timedLyricsData")!!

        return linesData.map {
            val json = it.jsonObject
            val line = json.getString("lyricLine")!!
            val range = json.getJsonObject("cueRange")?.let { range ->
                val start = range.getLong("startTimeMilliseconds") ?: return@let null
                val end = range.getLong("endTimeMilliseconds") ?: return@let null
                start..end
            } ?: error("Could not calculate range")

            Lyrics.Line(line, range)
        }
    }
// /contents/tabbedSearchResultsRenderer/tabs/0/tabRenderer/content/sectionListRenderer/contents/1/musicCardShelfRenderer/buttons/0/buttonRenderer/command/watchEndpoint/videoId
private fun notFound(key: String): Nothing = error("Could not find expected key: $key")
