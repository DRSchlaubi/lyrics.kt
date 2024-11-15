package dev.schlaubi.lyrics.lavalink

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "plugins.lyrics")
@Component
class LyricsConfig {
    var countryCode: String? = null
}