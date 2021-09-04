package com.stevehechio.apps.tvshowmanager.ui.viewmodels

import androidx.lifecycle.*
import com.stevehechio.apps.tvshowmanager.CreateMovieMutation
import com.stevehechio.apps.tvshowmanager.data.Resource
import com.stevehechio.apps.tvshowmanager.data.repository.AddShowRepository
import com.stevehechio.apps.tvshowmanager.type.CreateMovieInput
import javax.inject.Inject

/**
 * Created by stevehechio on 9/4/21
 */
class AddShowViewModel @Inject constructor(private val addShowRepository: AddShowRepository)
    : ViewModel() {

    fun createMovies(input: CreateMovieInput): LiveData<Resource<CreateMovieMutation.CreateMovie>> {
        return addShowRepository.uploadMovie(input)
    }

    override fun onCleared() {
        super.onCleared()
        addShowRepository.clear()
    }
}