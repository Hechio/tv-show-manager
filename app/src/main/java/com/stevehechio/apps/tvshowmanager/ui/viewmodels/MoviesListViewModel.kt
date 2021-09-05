package com.stevehechio.apps.tvshowmanager.ui.viewmodels

import androidx.lifecycle.*
import com.stevehechio.apps.tvshowmanager.data.repository.MoviesListRepository
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 * Created by stevehechio on 9/4/21
 */
class MoviesListViewModel @Inject constructor(private val moviesListRepository: MoviesListRepository) :
    ViewModel() {

    private val pageCursor: MutableLiveData<String?> = MutableLiveData()

    init {
        getMoviesLists(null)
    }

    private val movieItemFlow = pageCursor.asFlow().flatMapLatest { search ->
        moviesListRepository.loadMovieList(search)
    }

    val moviesItem = movieItemFlow.asLiveData()


    fun getMoviesLists(cursor: String?) {
        pageCursor.value = cursor
    }

    override fun onCleared() {
        super.onCleared()
        moviesListRepository.clear()
    }
}