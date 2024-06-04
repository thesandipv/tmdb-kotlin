package app.moviebase.tmdb.api

import app.moviebase.tmdb.core.endPointV3
import app.moviebase.tmdb.core.getByPaths
import app.moviebase.tmdb.core.parameterAppendResponses
import app.moviebase.tmdb.core.parameterIncludeImageLanguage
import app.moviebase.tmdb.core.parameterLanguage
import app.moviebase.tmdb.core.parameterPage
import app.moviebase.tmdb.model.AppendResponse
import app.moviebase.tmdb.model.TmdbCredits
import app.moviebase.tmdb.model.TmdbExternalIds
import app.moviebase.tmdb.model.TmdbImages
import app.moviebase.tmdb.model.TmdbMovieDetail
import app.moviebase.tmdb.model.TmdbMoviePageResult
import app.moviebase.tmdb.model.TmdbTranslations
import app.moviebase.tmdb.model.TmdbWatchProviderResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get

class TmdbMoviesApi internal constructor(private val client: HttpClient) {

    /**
     * Get the primary information about a movie.
     */
    suspend fun getDetails(
        movieId: Int,
        language: String? = null,
        appendResponses: Iterable<AppendResponse>? = null
    ): TmdbMovieDetail = client.getByPaths(*moviePath(movieId)) {
        parameterLanguage(language)
        parameterAppendResponses(appendResponses)
    }

    suspend fun getImages(
        movieId: Int,
        language: String? = null,
        includeImageLanguage: String? = null
    ): TmdbImages = client.getByPaths(*moviePath(movieId, "images")) {
        parameterLanguage(language)
        parameterIncludeImageLanguage(includeImageLanguage)
    }

    suspend fun getExternalIds(movieId: Int): TmdbExternalIds = client.getByPaths(*moviePath(movieId, "external_ids"))

    suspend fun getTranslations(movieId: Int): TmdbTranslations = client.getByPaths(*moviePath(movieId, "translations"))

    suspend fun getWatchProviders(movieId: Int): TmdbWatchProviderResult = client.getByPaths(*moviePath(movieId, "watch", "providers"))

    suspend fun popular(
        page: Int,
        language: String? = null,
    ): TmdbMoviePageResult = client.get {
        endPointV3("movie", "popular")
        parameterPage(page)
        parameterLanguage(language)
    }.body()

    suspend fun getRecommendations(
        movieId: Int,
        page: Int,
        language: String? = null,
    ): TmdbMoviePageResult = client.get {
        endPointMovie(movieId, "recommendations")
        parameterPage(page)
        parameterLanguage(language)
    }.body()

    private fun HttpRequestBuilder.endPointMovie(movieId: Int, vararg paths: String) {
        endPointV3("movie", movieId.toString(), *paths)
    }

    private fun moviePath(movieId: Int, vararg paths: String) = arrayOf("movie", movieId.toString(), *paths)

    suspend fun credits(
        movieId: Int,
        language: String? = null,
    ): TmdbCredits = client.get {
        endPointMovie(movieId, "credits")
        parameterLanguage(language)
    }.body()

}
