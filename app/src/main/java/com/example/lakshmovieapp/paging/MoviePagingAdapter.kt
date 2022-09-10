package com.example.lakshmovieapp.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lakshmovieapp.R
import com.example.lakshmovieapp.databinding.ItemRvMoviesBinding
import com.example.lakshmovieapp.model.movielist.Result
import com.example.lakshmovieapp.ui.MovieListFragmentDirections
import com.example.lakshmovieapp.utils.Constants
import com.example.lakshmovieapp.utils.Helper
import javax.inject.Inject

class MoviePagingAdapter @Inject constructor() :
    PagingDataAdapter<Result, MoviePagingAdapter.MyViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            Glide
                .with(holder.itemView)
                .load(Constants.IMG_BASE_URL + item.backdrop_path)
                .placeholder(R.drawable.round_rectangle_10)
                .centerCrop()
                .into(holder.binding.imgMovie)
            holder.binding.txtMovieName.isSelected = true
            holder.binding.txtMovieName.text = item.title
            holder.binding.txtMovieYear.text = Helper.parseDateyyyy(item.release_date)
            holder.binding.txtMovieRating.text = "${item.vote_average}  "
            holder.binding.root.setOnClickListener {
                holder.binding.root.findNavController()
                    .navigate(
                        MovieListFragmentDirections.actionMovieListToMovieDetails(item.id)
                    )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRvMoviesBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    class MyViewHolder(val binding: ItemRvMoviesBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}





















