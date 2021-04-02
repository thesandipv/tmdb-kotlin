package app.moviebase.tmdb.api

import app.moviebase.tmdb.model.AppendResponse
import app.moviebase.tmdb.model.TmdbSeason
import app.moviebase.tmdb.model.TmdbTranslations
import io.ktor.client.*
import io.ktor.client.request.*

class TmdbShowSeasonsApi(private val client: HttpClient) {

    suspend fun getDetails(showId: Int, seasonNumber: Int, language: String, appendResponses: Iterable<AppendResponse>? = null): TmdbSeason = client.get {
        endPointV3("tv", showId.toString(), "season", seasonNumber.toString())

        parameterLanguage(language)
        appendResponses?.let { parameterAppendResponses(it) }
    }

    suspend fun getTranslations(id: Int, seasonNumber: Int): TmdbTranslations = client.get {
        endPointV3("tv", id.toString(), "season", seasonNumber.toString(), "translations")
    }

}