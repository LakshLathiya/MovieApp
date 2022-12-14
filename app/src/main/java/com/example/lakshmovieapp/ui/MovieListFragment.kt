package com.example.lakshmovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lakshmovieapp.databinding.FragmentMovieListBinding
import com.example.lakshmovieapp.paging.LoaderAdapter
import com.example.lakshmovieapp.paging.MoviePagingAdapter
import com.example.lakshmovieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel by viewModels<MovieViewModel>()

    @Inject
    lateinit var moviePagingAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()

    }

    private fun initialize() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Movie App"

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = moviePagingAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter { moviePagingAdapter.retry() },
                footer = LoaderAdapter { moviePagingAdapter.retry() }
            )
        }

        movieViewModel.list.observe(viewLifecycleOwner, Observer {
            moviePagingAdapter.submitData(lifecycle, it)
        })

        viewLifecycleOwner.lifecycleScope.launch {
            moviePagingAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.shimmerViewContainer.apply {
                    startShimmerAnimation()
                    isVisible = loadStates.refresh is LoadState.Loading
                }
                binding.layoutError.apply {
                    root.isVisible = loadStates.refresh is LoadState.Error
                    btnRetry.setOnClickListener {
                        moviePagingAdapter.retry()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}