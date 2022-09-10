package com.example.lakshmovieapp.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lakshmovieapp.model.movielist.Result
import com.example.lakshmovieapp.remote.MovieApi
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val movieAPI: MovieApi) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val position = params.key ?: 1
            val response = movieAPI.getMovies(position)
            return LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.body()!!.total_pages) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}