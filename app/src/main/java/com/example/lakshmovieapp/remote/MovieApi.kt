package com.example.lakshmovieapp.remote

import com.example.lakshmovieapp.model.movieDetails.MovieDetails
import com.example.lakshmovieapp.model.movielist.Movies
import com.example.lakshmovieapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("api_key") appId: String = Constants.API_KEY
    ): Response<Movies>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") appId: String = Constants.API_KEY
    ): Response<MovieDetails>
}