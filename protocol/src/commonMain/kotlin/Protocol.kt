package dev.schlaubi.lyrics.protocol

import dev.schlaubi.lyrics.protocol.Lyrics.Line
import dev.schlaubi.lyrics.protocol.Lyrics.Track
import kotlinx.serialization.Serializable

/**
 * Representation of a track's lyrics.
 *
 * @property track the [Track] the lyrics are for
 * @property source the source for the lyrics
 * @property lines a list of [lyric lines][Line]
 */
@Serializable
public data class Lyrics(
    val track: Track,
    val source: String,
    val lines: List<Line>
) {

    /**
     * Representation of a lyrics track.
     *
     * @property title the title of the track
     * @property author the author of the track
     * @property album the album the track is in
     */
    @Serializable
    public data class Track(val title: String, val author: String, val album: String)

    /**
     * Representation of a lyrics line.
     *
     * @property line the line
     * @property range the range (in ms) where the line is present in the song
     */
    @Serializable
    public data class Line(
        val line: String,
        val range: SerializableLongRange
    )
}

/**
 * Representation of a search track.
 *
 * @property videoId the id of the YouTube video
 * @property title the title of the track
 */
@Serializable
public data class SearchTrack(
    val videoId: String,
    val title: String
)
