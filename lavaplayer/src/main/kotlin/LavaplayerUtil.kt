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
public suspend fun LyricsClient.findLyrics(track: AudioTrack): Lyrics {
    val videoId = when {
        track.sourceManager.sourceName == "youtube" -> track.info.identifier
        track.info.isrc != null -> search(track.info.isrc).firstOrNull()?.videoId ?: throw LyricsNotFoundException()
        else -> search("${track.info.title} - ${track.info.author}").firstOrNull()?.videoId
            ?: throw LyricsNotFoundException()
    }

    return requestLyrics(videoId)
}

/**
 * Finds the lyrics for [track].
 *
 * @see CompletionStage
 */
public fun LyricsClient.findLyricsAsync(track: AudioTrack): CompletionStage<Lyrics> = future { findLyrics(track) }

/**
 * Finds the lyrics for [track].
 *
 * **Important:** This method blocks the current thread
 */
@JvmName("findLyrics")
public fun LyricsClient.findLyricsBlocking(track: AudioTrack): Lyrics =
    findLyricsAsync(track).toCompletableFuture().join()
