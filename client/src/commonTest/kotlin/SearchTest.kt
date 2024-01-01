import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SearchTest {

    @Test
    fun `search for isrc`() = withClient {
        val result = search("frx762175060", "de")
        println(result)

        assertEquals(1, result.size)
        val track= result.first()
        assertEquals("8ePP9MTcO5w", track.videoId)
    }

    @Test
    fun `search for x`() = withClient {
        val result = search("high hopes", "de")
        println(result)

        val track= result.first()
        assertEquals("GJY8OMJXRAk", track.videoId)
    }
}