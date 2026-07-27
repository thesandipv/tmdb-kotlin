package app.moviebase.tmdb.core

import app.moviebase.tmdb.model.TmdbErrorResponse
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class TmdbExceptionTest {

    private val errorResponse = TmdbErrorResponse(
        statusCode = 34,
        statusMessage = "The resource you requested could not be found.",
    )

    @Test
    fun `message contains status code and status message`() {
        val exception = TmdbException(errorResponse)

        assertThat(exception.message).isEqualTo(
            "Status code: 34. Message: \"The resource you requested could not be found.\""
        )
    }

    @Test
    fun `message does not contain URL fragment when requestUrl is null`() {
        val exception = TmdbException(errorResponse)

        assertThat(exception.message).doesNotContain("URL:")
    }

    @Test
    fun `message includes request URL when provided`() {
        val url = "https://api.themoviedb.org/3/movie/99999?language=en"
        val exception = TmdbException(errorResponse, requestUrl = url)

        assertThat(exception.message).isEqualTo(
            "Status code: 34. Message: \"The resource you requested could not be found.\". URL: $url"
        )
    }

    @Test
    fun `statusCode property returns the response status code`() {
        val exception = TmdbException(errorResponse, requestUrl = "https://x")

        assertThat(exception.statusCode).isEqualTo(34)
    }

    @Test
    fun `caused throwable is preserved`() {
        val cause = RuntimeException("network down")
        val exception = TmdbException(errorResponse, requestUrl = null, caused = cause)

        assertThat(exception.cause).isSameInstanceAs(cause)
    }
}
