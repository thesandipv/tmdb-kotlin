package app.moviebase.tmdb.model

import app.moviebase.tmdb.core.TmdbInstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Expires date is parsed from UTC datetime.
 */
@Serializable
data class TmdbRequestToken(
    @SerialName("success") val success: Boolean,
    @SerialName("expires_at")
    @Serializable(TmdbInstantSerializer::class)
    val expiredAt: Instant?,
    @SerialName("request_token") val requestToken: String
)

@Serializable
data class TmdbSession(
    @SerialName("success") val success: Boolean,
    @SerialName("session_id") val sessionId: String
)

/**
 * Expires date is parsed from UTC datetime.
 */
@Serializable
data class TmdbGuestSession(
    @SerialName("success") val success: Boolean,
    @SerialName("expires_at")
    @Serializable(TmdbInstantSerializer::class)
    val expiredAt: Instant?,
    @SerialName("guest_session_id") val guestSessionId: String
)

@Serializable
data class TmdbDeleteSession(
    @SerialName("success") val success: Boolean,
)
