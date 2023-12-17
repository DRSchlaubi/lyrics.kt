import org.junit.jupiter.api.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals

@Ignore //This test is personalized
class SearchTest {

    @Test
    fun `search for isrc`() = withClient {
        val result = search("gbdhc2227201")

        assertEquals(1, result.size)
        val track= result.first()
        assertEquals("We Got the Moves", track.title)
    }
}