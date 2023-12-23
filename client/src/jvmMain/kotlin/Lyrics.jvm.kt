package dev.schlaubi.lyrics

import io.ktor.client.*

internal actual fun newClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(config)
