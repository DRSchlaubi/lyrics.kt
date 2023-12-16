package dev.schlaubi.lyrics.internal.model

import io.ktor.resources.*

@Resource("youtubei/v1")
internal class MusicApi {
    @Resource("browse")
    data class Browse(val parent: MusicApi = MusicApi())

    @Resource("next")
    data class Next(val parent: MusicApi = MusicApi())

    @Resource("search")
    data class Search(val parent: MusicApi = MusicApi())
}
