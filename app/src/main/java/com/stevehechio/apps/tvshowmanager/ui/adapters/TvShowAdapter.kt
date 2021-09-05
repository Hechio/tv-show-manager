package com.stevehechio.apps.tvshowmanager.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stevehechio.apps.tvshowmanager.MoviesListQuery
import com.stevehechio.apps.tvshowmanager.databinding.TvShowListItemBinding
import com.stevehechio.apps.tvshowmanager.utils.DateUtils
import com.stevehechio.apps.tvshowmanager.utils.gone
import com.stevehechio.apps.tvshowmanager.utils.visible

/**
 * Created by stevehechio on 9/3/21
 */
class TvShowAdapter :  RecyclerView.Adapter<TvShowAdapter.TVShowHolder>(){
    private var moviesList = arrayListOf<MoviesListQuery.Edge?>()
    var onEndOfListReached: (() -> Unit)? = null


    fun addMoviesList(list: List<MoviesListQuery.Edge?>){
        moviesList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList(){
        moviesList.clear()
        notifyDataSetChanged()
    }
    inner class  TVShowHolder(private val binding: TvShowListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViews(movie: MoviesListQuery.Edge){
            binding.tvTitle.text = movie.node?.title ?: ""
            val date: String? = movie.node?.releaseDate?.toString()
            if (date!=null){
                binding.tvReleaseDate.visible()
                binding.tvReleaseDate.text = DateUtils.formatGMTDateStr(date)
            }else {
                binding.tvReleaseDate.gone()
            }

            binding.tvSeasons.text = movie.node?.seasons.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
       return TVShowHolder(TvShowListItemBinding.inflate(
           LayoutInflater.from(parent.context),parent,false
       ))
    }

    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {
        if (position == itemCount - 2){
            onEndOfListReached?.invoke()
        }
        moviesList[position].let {
            if (it != null) {
                holder.bindViews(it)
            }
        }
    }

    override fun getItemCount(): Int {
       return moviesList.size
    }

}
