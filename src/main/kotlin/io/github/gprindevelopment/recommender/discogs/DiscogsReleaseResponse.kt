package io.github.gprindevelopment.recommender.discogs

import com.fasterxml.jackson.annotation.JsonProperty

data class DiscogsReleaseResponse(
    val artists: List<Artist> = emptyList(),
    val notes: String? = null,
    val genres: List<String> = emptyList(),
    val styles: List<String> = emptyList(),
    @JsonProperty("tracklist")
    val trackList: List<Track> = emptyList(),
    @JsonProperty("extraartists")
    val extraArtists: List<Artist> = emptyList()
)

data class Artist(
    val name: String,
    val role: String? = null
)

data class Track(
    val title: String,
    val position: String? = null,
    @JsonProperty("extraartists")
    val extraArtists: List<Artist>? = emptyList(),
    val duration: String? = null
)