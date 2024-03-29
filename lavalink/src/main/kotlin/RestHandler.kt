package dev.schlaubi.lyrics.lavalink

import dev.schlaubi.lyrics.LyricsClient
import dev.schlaubi.lyrics.LyricsNotFoundException
import dev.schlaubi.lyrics.lavaplayer.findLyrics
import dev.schlaubi.lyrics.protocol.Lyrics
import dev.schlaubi.lyrics.protocol.SearchTrack
import kotlinx.coroutines.runBlocking
import lavalink.server.io.SocketServer
import lavalink.server.util.socketContext
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class RestHandler(private val socketServer: SocketServer, private val config: Config) {
    private val client = LyricsClient()

    @GetMapping(value = ["/v4/lyrics/{videoId}"])
    fun getLyrics(@PathVariable("videoId") videoId: String): Lyrics = runBlocking {
        try {
            client.requestLyrics(videoId)
        } catch (e: LyricsNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping(value = ["/v4/lyrics/search/{query}"])
    fun searchLyrics(@PathVariable("query") query: String): List<SearchTrack> = runBlocking {
        client.search(query, config.countryCode)
    }

    @GetMapping(value = ["/v4/sessions/{sessionId}/players/{guildId}/lyrics"])
    fun getLyricsOfPlayingTrack(@PathVariable("sessionId") sessionId: String, @PathVariable("guildId") guildId: Long) = runBlocking {
        val track = socketContext(socketServer, sessionId).getPlayer(guildId).track
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not currently playing anything")

        try {
            client.findLyrics(track)
        } catch (e: LyricsNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}
