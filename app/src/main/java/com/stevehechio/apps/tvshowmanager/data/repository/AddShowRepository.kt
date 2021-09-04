package com.stevehechio.apps.tvshowmanager.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stevehechio.apps.tvshowmanager.CreateMovieMutation
import com.stevehechio.apps.tvshowmanager.data.Resource
import com.stevehechio.apps.tvshowmanager.data.api.TvShowApiService
import com.stevehechio.apps.tvshowmanager.data.base.BaseRepository
import com.stevehechio.apps.tvshowmanager.type.CreateMovieInput
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by stevehechio on 9/4/21
 */
class AddShowRepository @Inject constructor(private var apiService: TvShowApiService):
    BaseRepository {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var result = MutableLiveData<Resource<CreateMovieMutation.CreateMovie>>()

    fun uploadMovie(input: CreateMovieInput?): LiveData<Resource<CreateMovieMutation.CreateMovie>>{

        if (input == null){
            return result
        }
        addDisposable(apiService.createMovie(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStartFetching() }
            .subscribe({
                    response -> onSuccess(response)
            },{
                onErrorOcurred(it)
            }))
        return result

    }

    private fun onErrorOcurred(it: Throwable) {
        Log.e("Create Movie", it.localizedMessage, it)
        result.value = Resource.Failure(it.localizedMessage)
    }

    private fun onSuccess(response: CreateMovieMutation.CreateMovie) {
        result.value = Resource.Success(response)
    }

    private fun onStartFetching() {
        result.value = Resource.Loading()
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}