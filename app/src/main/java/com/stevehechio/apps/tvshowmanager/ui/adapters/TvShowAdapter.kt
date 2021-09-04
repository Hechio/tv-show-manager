package com.stevehechio.apps.tvshowmanager.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stevehechio.apps.tvshowmanager.MoviesListQuery
import com.stevehechio.apps.tvshowmanager.databinding.TvShowListItemBinding
import com.stevehechio.apps.tvshowmanager.utils.DateUtils

/**
 * Created by stevehechio on 9/3/21
 */
class TvShowAdapter :  ListAdapter<MoviesListQuery.Edge,TvShowAdapter.TVShowHolder>(ShowsDiffUtil()){

    inner class  TVShowHolder(val binding: TvShowListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViews(movie: MoviesListQuery.Edge){
            binding.tvTitle.text = movie.node?.title ?: ""
            val date: String? = movie.node?.releaseDate?.toString()
            if (date!=null){
                binding.tvReleaseDate.text = DateUtils.formatGMTDateStr(date)
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
        getItem(position).let { holder.bindViews(it) }
    }

}

class ShowsDiffUtil: DiffUtil.ItemCallback<MoviesListQuery.Edge>(){
    override fun areItemsTheSame(
        oldItem: MoviesListQuery.Edge,
        newItem: MoviesListQuery.Edge
    ): Boolean {
        return oldItem.node?.id  == newItem.node?.id
    }

    override fun areContentsTheSame(
        oldItem: MoviesListQuery.Edge,
        newItem: MoviesListQuery.Edge
    ): Boolean {
      return oldItem == newItem
    }
}