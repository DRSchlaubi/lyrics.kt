import org.junit.jupiter.api.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals

class SearchTest {

    @Test
    fun `search for isrc`() = withClient {
        val result = search("frx762175060")
        println(result)

        assertEquals(1, result.size)
        val track= result.first()
        assertEquals("We Got the Moves", track.title)
    }
}