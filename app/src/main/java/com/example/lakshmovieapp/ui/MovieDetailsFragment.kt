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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lakshmovieapp.R
import com.example.lakshmovieapp.databinding.FragmentMovieDetailsBinding
import com.example.lakshmovieapp.model.movieDetails.MovieDetails
import com.example.lakshmovieapp.adapter.GenresRVAdapter
import com.example.lakshmovieapp.utils.Constants
import com.example.lakshmovieapp.utils.Helper
import com.example.lakshmovieapp.utils.NetworkResult
import com.example.lakshmovieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieDetailsFragmentArgs>()

    private val movieViewModel by viewModels<MovieViewModel>()

    @Inject
    lateinit var genresRVAdapter: GenresRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.getMovieDetails(args.movieId)
        initialize()
        bindObservers()
    }

    private fun initialize() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Details"
        binding.rvGenres.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGenres.setHasFixedSize(true)
        binding.rvGenres.adapter = genresRVAdapter

        binding.layoutError.btnRetry.setOnClickListener {
            movieViewModel.getMovieDetails(args.movieId)
            bindObservers()
        }
    }

    private fun bindObservers() {
        movieViewModel.movieDetailsLiveData.observe(viewLifecycleOwner, Observer {
            binding.shimmerViewContainer.apply {
                stopShimmerAnimation()
                isVisible = false
            }
            when (it) {
                is NetworkResult.Success -> {
                    binding.layoutError.root.visibility = View.GONE
                    bindValueToUI(it.data!!)
                }
                is NetworkResult.Error -> {
                    binding.apply {
                        layoutError.root.visibility = View.VISIBLE
                        layoutError.txtError.text = it.message
                    }
                }
                is NetworkResult.Loading -> {
                    binding.shimmerViewContainer.apply {
                        startShimmerAnimation()
                        isVisible = true
                    }
                }
            }
        })
    }

    private fun bindValueToUI(movieDetails: MovieDetails) {
        binding.apply {
            Glide
                .with(binding.root)
                .load(Constants.IMG_BASE_URL + movieDetails.poster_path)
                .placeholder(R.drawable.round_rectangle_10)
                .into(imgMovie)
            for (i in movieDetails.genres!!) {
                genresRVAdapter.setGenresList(i)
            }
            txtMovieName.isSelected = true
            txtMovieName.text = movieDetails.title
            txtMovieYear.text = Helper.parseDateyyyy(movieDetails.release_date)
            txtMovieRating.text = "${movieDetails.vote_average}/10"
            txtMovieDetails.text = movieDetails.overview
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}