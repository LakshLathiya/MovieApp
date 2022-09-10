package com.example.lakshmovieapp.model.movieDetails

data class MovieDetails(
    val genres: List<Genre>? = null,
    val overview: String? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val vote_average: Double? = null,
)