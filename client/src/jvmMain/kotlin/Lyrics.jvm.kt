package dev.schlaubi.lyrics

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

internal actual fun newClient(config: HttpClientConfig<*>.() -> Unit):HttpClient = HttpClient(OkHttp, config)
