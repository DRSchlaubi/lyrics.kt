@file:JvmName("LavaplayerUtil")

package dev.schlaubi.lyrics.lavaplayer

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.schlaubi.lyrics.LyricsClient
import dev.schlaubi.lyrics.LyricsNotFoundException
import dev.schlaubi.lyrics.protocol.Lyrics
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletionStage

@JvmName("findLyricsSuspending")
public suspend fun LyricsClient.findLyrics(track: AudioTrack): Lyrics {
    val videoId = when {
        track is YoutubeAudioTrack -> track.info.identifier
        track.info.isrc != null -> search(track.info.isrc).firstOrNull()?.videoId ?: throw LyricsNotFoundException()
        else -> search("${track.info.title} - ${track.info.author}").firstOrNull()?.videoId
            ?: throw LyricsNotFoundException()
    }

    return requestLyrics(videoId)
}

public fun LyricsClient.findLyricsAsync(track: AudioTrack): CompletionStage<Lyrics> = future {
    findLyrics(track)
}

@JvmName("findLyrics")
public fun LyricsClient.findLyricsBlocking(track: AudioTrack): Lyrics =
    findLyricsAsync(track).toCompletableFuture().join()
