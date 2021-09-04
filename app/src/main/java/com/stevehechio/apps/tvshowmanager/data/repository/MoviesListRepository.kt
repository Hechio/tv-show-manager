package com.stevehechio.apps.tvshowmanager.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stevehechio.apps.tvshowmanager.MoviesListQuery
import com.stevehechio.apps.tvshowmanager.data.Resource
import com.stevehechio.apps.tvshowmanager.data.api.TvShowApiService
import com.stevehechio.apps.tvshowmanager.data.base.BaseRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by stevehechio on 9/4/21
 */
class MoviesListRepository @Inject constructor(private var apiService: TvShowApiService): BaseRepository {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val result = MutableLiveData<Resource<MoviesListQuery.Movies>>()

    fun loadMovieList(): LiveData<Resource<MoviesListQuery.Movies>> {

        addDisposable(apiService.fetchMovies()
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
        result.value = Resource.Failure(it.toString())
    }

    private fun onSuccess(response: MoviesListQuery.Movies?) {
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