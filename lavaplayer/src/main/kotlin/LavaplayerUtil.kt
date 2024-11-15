@file:JvmName("LavaplayerUtil")

package dev.schlaubi.lyrics.lavaplayer

import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.schlaubi.lyrics.LyricsClient
import dev.schlaubi.lyrics.LyricsNotFoundException
import dev.schlaubi.lyrics.protocol.Lyrics
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletionStage

/**
 * Finds the lyrics for [track].
 */
@JvmName("findLyricsSuspending")
public suspend fun LyricsClient.findLyrics(track: AudioTrack, region: String? = null): Lyrics {
    val videoId = when {
        track.sourceManager.sourceName == "youtube" -> track.info.identifier
        track.info.isrc != null -> search("\"${track.info.isrc}\"", region).firstOrNull()?.videoId ?: throw LyricsNotFoundException()
        else -> search("${track.info.title} - ${track.info.author}", region).firstOrNull()?.videoId
            ?: throw LyricsNotFoundException()
    }

    return requestLyrics(videoId)
}

/**
 * Finds the lyrics for [track].
 *
 * @see CompletionStage
 */
@JvmOverloads
public fun LyricsClient.findLyricsAsync(track: AudioTrack, region: String? = null): CompletionStage<Lyrics> =
    future { findLyrics(track, region) }

/**
 * Finds the lyrics for [track].
 *
 * **Important:** This method blocks the current thread
 */
@JvmOverloads
@JvmName("findLyrics")
public fun LyricsClient.findLyricsBlocking(track: AudioTrack, region: String? = null): Lyrics =
    findLyricsAsync(track, region).toCompletableFuture().join()

