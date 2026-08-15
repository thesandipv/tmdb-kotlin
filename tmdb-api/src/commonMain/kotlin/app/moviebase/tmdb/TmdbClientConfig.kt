package app.moviebase.tmdb

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.logging.LoggingConfig

class TmdbClientConfig {

    var tmdbApiKey: String? = null
    internal var tmdbAuthCredentials: TmdbAuthCredentials? = null

    var expectSuccess: Boolean = false
    var useCache: Boolean = true
    var useTimeout: Boolean = true
    var maxRequestRetries: Int? = 5

    internal var httpClientConfigBlock: (HttpClientConfig<*>.() -> Unit)? = null
    internal var httpClientBuilder: (() -> HttpClient)? = null
    internal var httpClientLoggingBlock: (LoggingConfig.() -> Unit)? = null

    fun userAuthentication(block: TmdbAuthCredentials.() -> Unit) {
        tmdbAuthCredentials = TmdbAuthCredentials().apply(block)
    }

    fun logging(block: LoggingConfig.() -> Unit) {
        httpClientLoggingBlock = block
    }

    /**
     * Set custom HttpClient configuration for the default HttpClient.
     */
    fun httpClient(block: HttpClientConfig<*>.() -> Unit) {
        this.httpClientConfigBlock = block
    }

    /**
     * Creates an custom [HttpClient] with the specified [HttpClientEngineFactory] and optional [block] configuration.
     * Note that the TMDB config will be added afterwards.
     */
    fun <T : HttpClientEngineConfig> httpClient(
        engineFactory: HttpClientEngineFactory<T>,
        block: HttpClientConfig<T>.() -> Unit = {},
    ) {
        httpClientBuilder = {
            HttpClient(engineFactory, block)
        }
    }

    fun httpClient(engine: HttpClientEngine, block: HttpClientConfig<*>.() -> Unit = {}) {
        httpClientBuilder = {
            HttpClient(engine, block)
        }
    }

    companion object {

        internal fun withKey(tmdbApiKey: String) = TmdbClientConfig().apply {
            this.tmdbApiKey = tmdbApiKey
        }
    }
}

class TmdbAuthCredentials {

    // used in version 4
    var authenticationToken: String? = null

    internal var sessionIdProvider: (suspend () -> String?)? = null
    internal var guestSessionIdProvider: (suspend () -> String?)? = null
    internal var accessTokenProvider: (suspend () -> String?)? = null

    fun loadSessionId(provider: suspend () -> String?) {
        sessionIdProvider = provider
    }

    fun loadGuestSessionId(provider: suspend () -> String?) {
        guestSessionIdProvider = provider
    }

    fun loadAccessToken(provider: suspend () -> String?) {
        accessTokenProvider = provider
    }
}
