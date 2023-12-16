import dev.schlaubi.lyrics.protocol.Lyrics
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LyricsTest {

    @Test
    fun `search for lyrics`() = withClient {
        val lyrics = requestLyrics("AhTypsCd3dM")

        val parsed = Json.decodeFromString<Lyrics>(ClassLoader.getSystemResource("wegotthemoves.json").readText())
        assertEquals(parsed, lyrics)
    }
}