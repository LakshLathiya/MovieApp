package com.example.lakshmovieapp.model.movielist

data class Movies(
    val page: Int? = null,
    val results: List<Result>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)