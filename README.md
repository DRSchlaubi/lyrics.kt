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
LavaplayerUtil.findLyrics(client, player.getPlayingTrack());
```

# Using with Lavalink

Replace x.y.z with the current version

```yaml
lavalink:
  plugins:
    - dependency: "dev.schlaubi.lyrics:lavalink:x.y.z"
      repository: "https://maven.lavalink.dev/releases" # this is optional for lavalink v4.0.0-beta.5 or greater
      snapshot: false # set to true if you want to use snapshot builds (see below)
plugins:
  lyrics:
    countryCode: de #country code for resolving isrc tracks
```

## API for clients
```json5
// GET /v4/lyrics/{videoId}
// GET /v4/sessions/{sessionId}/players/{guildId}/lyrics

{
  "type": "timed",
  // can also be text
  "track": {
    "title": "We Got the Moves",
    "author": "Electric Callboy",
    "album": "We Got the Moves",
    "albumArt": [
      {
        "url": "https://lh3.googleusercontent.com/rDaGBvVRyBbHKJuQFFfIUuCLU36OuHHTjDz2u9xDwbIgD2MWM_P6L2L01IOOtoJvi7ks43OFeCqx0cRp=w60-h60-l90-rj",
        "height": 60,
        "width": 60
      }
    ]
  },
  "source": "LyricFind",
  "text": "<lyrics>",
  // Only present for type text
  // Only present for type timed
  "lines": [
    {
      "line": "♪",
      "range": {
        "start": 0,
        // start timestamp in ms since track start
        "end": 6510
        // end timestamp in ms since track start
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
