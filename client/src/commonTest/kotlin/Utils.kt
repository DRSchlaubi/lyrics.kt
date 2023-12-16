import dev.schlaubi.lyrics.LyricsClient
import kotlinx.coroutines.test.runTest

fun withClient(block: suspend LyricsClient.() -> Unit) {
    val client = LyricsClient()

    runTest {
        client.use {
            block(it)
        }
    }
}
