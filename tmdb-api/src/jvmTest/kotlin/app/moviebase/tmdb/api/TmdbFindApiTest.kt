package app.moviebase.tmdb.api

import app.moviebase.tmdb.model.TmdbExternalSource
import app.moviebase.tmdb.model.TmdbGender
import app.moviebase.tmdb.core.mockHttpClient
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class TmdbFindApiTest {

    val client = mockHttpClient(
        version = 3,
        responses = mapOf(
            "find/tt10919380?language=en&external_source=imdb_id"
                to "find/find_movie_tt10919380.json",
            "find/nm0424060?language=en&external_source=imdb_id"
                to "find/find_person_nm0424060.json"
        )
    )

    val classToTest = TmdbFindApi(client)

    @Test
    fun `it can find movie by IMDB id`() = runTest {
        val pageResult = classToTest.find("tt10919380", "en", TmdbExternalSource.IMDB)

        assertThat(pageResult.movieResults.size).isEqualTo(1)
        val movie = pageResult.movieResults.first()
        assertThat(movie.id).isEqualTo(551804)
    }

    @Test
    fun `it can find person with non binary gender by IMDB id`() = runTest {
        val pageResult = classToTest.find("nm0424060", "en", TmdbExternalSource.IMDB)

        assertThat(pageResult.personResults.size).isEqualTo(1)
        val person = pageResult.personResults.first()
        assertThat(person.id).isEqualTo(1245)
        assertThat(person.gender).isEqualTo(TmdbGender.NON_BINARY)
    }
}
