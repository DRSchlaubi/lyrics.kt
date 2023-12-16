# lyrics.kt

A very simple lyrics client based on YouTube

# Using from Kotlin

````kotlin
val client = LyricsClient()

val (videoId) = lyrics.search("Electric callboy we got the moves").first()

client.requestLyrics(videoId)
````

# Using from Lavaplayer

```java
var client = new LyricsClient();
LavaplayerUtil.findLyrics(player.getPlayingTrack());
```

# Using with Lavalink

Replace x.y.z with the current version

```yaml
lavalink:
  plugins:
    - dependency: "dev.schlaubi.lyrics:lavalink:x.y.z"
      repository: "https://maven.lavalink.dev/releases" # this is optional for lavalink v4.0.0-beta.5 or greater
      snapshot: false # set to true if you want to use snapshot builds (see below)
```

## API for clients
```json5
// GET /v4/lyrics/{videoId}
// GET /v4/sessions/{sessionId}/players/{guildId}/lyrics

{
  "track": {
    "title": "We Got the Moves",
    "author": "Electric Callboy",
    "album": "We Got the Moves"
  },
  "source": "LyricFind",
  "lines": [
    {
      "line": "♪",
      "range": {
        "start": 0, // start timestamp in ms since track start
        "end": 6510 // end timestamp in ms since track start
      }
    },
    {
      "line": "Summer mood",
      "range": {
        "start": 6510,
        "end": 7330
      }
    }
  ]
}
```
```json5
// /v4/lyrics/search/{query}

[
  {"videoId": "UVXvQtm6ji0", "title": "We Got The moves"}
]
```
