import dev.schlaubi.lyrics.protocol.Lyrics
import dev.schlaubi.lyrics.protocol.TimedLyrics
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LyricsTest {

    @Test
    fun `search for timed`() = withClient {
        val lyrics = requestLyrics("AhTypsCd3dM")

        val parsed = Json.decodeFromString<Lyrics>(ClassLoader.getSystemResource("wegotthemoves.json").readText())
        assertEquals(parsed, lyrics)
    }
// Yt removed lyrics for this song and now my test failed LOL
//    @Test
//    fun `search for lyrics`() = withClient {
//        val lyrics = requestLyrics("gn8dzGmlhzU")
//
//        val parsed = Json.decodeFromString<Lyrics>(ClassLoader.getSystemResource("saygoodbye.json").readText())
//        assertEquals(parsed, lyrics)
//    }
}