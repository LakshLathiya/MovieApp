package com.example.lakshmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lakshmovieapp.databinding.ItemRvGenresBinding
import com.example.lakshmovieapp.model.movieDetails.Genre
import javax.inject.Inject

class GenresRVAdapter @Inject constructor() :
    RecyclerView.Adapter<GenresRVAdapter.MyViewHolder>() {

    var _genre = mutableListOf<Genre>()
    fun setGenresList(genre: Genre) {
        _genre.addAll(listOf(genre))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRvGenresBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genre = _genre[position]
        holder.binding.txtGeneres.text = genre.name

    }

    override fun getItemCount(): Int {
        return _genre.size
    }

    class MyViewHolder(val binding: ItemRvGenresBinding) :
        RecyclerView.ViewHolder(binding.root)
}



