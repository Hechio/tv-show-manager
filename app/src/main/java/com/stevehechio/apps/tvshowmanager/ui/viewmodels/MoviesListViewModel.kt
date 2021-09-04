package com.stevehechio.apps.tvshowmanager.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.stevehechio.apps.tvshowmanager.MoviesListQuery
import com.stevehechio.apps.tvshowmanager.data.Resource
import com.stevehechio.apps.tvshowmanager.data.repository.MoviesListRepository
import javax.inject.Inject

/**
 * Created by stevehechio on 9/4/21
 */
class MoviesListViewModel @Inject constructor(private val moviesListRepository: MoviesListRepository)
    : ViewModel() {
    lateinit var moviesLiveData: LiveData<Resource<MoviesListQuery.Movies>>

    init {
        getMoviesLists()
    }

    private fun getMoviesLists() {
        moviesLiveData = moviesListRepository.loadMovieList()
    }

    override fun onCleared() {
        super.onCleared()
        moviesListRepository.clear()
    }
}