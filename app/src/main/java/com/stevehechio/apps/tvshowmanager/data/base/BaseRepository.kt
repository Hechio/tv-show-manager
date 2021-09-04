package com.stevehechio.apps.tvshowmanager.data.base

import io.reactivex.rxjava3.disposables.Disposable

/**
 * Created by stevehechio on 9/4/21
 */
interface BaseRepository {
    fun addDisposable(disposable: Disposable)

    fun clear()
}