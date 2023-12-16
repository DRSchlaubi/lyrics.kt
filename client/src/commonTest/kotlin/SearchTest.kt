import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SearchTest {

    @Test
    fun `search for isrc`() = withClient {
        val result = search("gbdhc2227201")

        assertEquals(1, result.size)
        val track= result.first()
        assertEquals("AhTypsCd3dM", track.videoId)
        assertEquals("We Got the Moves", track.title)
    }
}