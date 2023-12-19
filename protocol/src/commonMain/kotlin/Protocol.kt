package dev.schlaubi.lyrics.protocol

import dev.schlaubi.lyrics.protocol.Lyrics.Track
import dev.schlaubi.lyrics.protocol.TimedLyrics.Line
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representation of a track's lyrics.
 *
 * @property track the [Track] the lyrics are for
 * @property source the source for the lyrics
 * @property text the lyrics as text
 */
@Serializable
public sealed interface Lyrics {
    public val track: Track
    public val source: String
    public val text: String

    /**
     * Representation of a lyrics track.
     *
     * @property title the title of the track
     * @property author the author of the track
     * @property album the album the track is in
     */
    @Serializable
    public data class Track(
        val title: String,
        val author: String,
        val album: String,
        val albumArt: List<AlbumArt>
    ) {
        /**
         * Representation of an album art.
         *
         * @property url the cdn url of the image
         * @property height the physical height of the image
         * @property width the physical width of the image
         */
        @Serializable
        public data class AlbumArt(
            val url: String,
            val height: Int,
            val width: Int
        )
    }
}

/**
 * Representation of a track's lyrics with timestamps.
 *
 * @property lines a list of [lyric lines][Line]
 */
@Serializable
@SerialName("timed")
public data class TimedLyrics(
    override val track: Track,
    override val source: String,
    val lines: List<Line>
) : Lyrics {
    override val text: String by lazy { lines.joinToString("\n") { it.line } }

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
 * Represents the lyrics of a track as text.
 *
 * @param track the track the lyrics are for
 * @param source the source for the lyrics
 * @param text the lyrics as text
 */
@Serializable
@SerialName("text")
public data class TextLyrics(
    override val track: Track,
    override val source: String,
    override val text: String
) : Lyrics

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
