package com.example.lakshmovieapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.lakshmovieapp.model.movieDetails.MovieDetails
import com.example.lakshmovieapp.paging.MoviePagingSource
import com.example.lakshmovieapp.remote.MovieApi
import com.example.lakshmovieapp.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    private val _movieLiveData = MutableLiveData<NetworkResult<MovieDetails>>()
    val movieLiveData get() = _movieLiveData

    fun getMovies() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { MoviePagingSource(movieApi) }
    ).liveData

    suspend fun getMovieDetails(movieId: Int) {
        _movieLiveData.postValue(NetworkResult.Loading())
        val response = movieApi.getMovieDetails(movieId)
        if (response.isSuccessful && response.body() != null) {
            _movieLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _movieLiveData.postValue(NetworkResult.Error(errorObj.getString("status_message")))
        } else {
            _movieLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}

