package app.moviebase.tmdb.core

import app.moviebase.tmdb.model.TmdbErrorResponse

@Suppress("MemberVisibilityCanBePrivate")
class TmdbException(
    val tmdbResponse: TmdbErrorResponse,
    val requestUrl: String? = null,
    caused: Throwable? = null,
) : IllegalStateException(buildMessage(tmdbResponse, requestUrl), caused) {

    val statusCode get() = tmdbResponse.statusCode

    companion object {
        private fun buildMessage(response: TmdbErrorResponse, url: String?): String {
            val base = "Status code: ${response.statusCode}. Message: \"${response.statusMessage}\""
            return if (url == null) base else "$base. URL: $url"
        }
    }
}
