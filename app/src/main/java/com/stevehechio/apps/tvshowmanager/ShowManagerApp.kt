package com.stevehechio.apps.tvshowmanager

import android.app.Application
import com.stevehechio.apps.tvshowmanager.di.components.DaggerAppComponent
import com.stevehechio.apps.tvshowmanager.di.modules.ApiModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by stevehechio on 9/3/21
 */
class ShowManagerApp : Application(), HasAndroidInjector {


    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .apiModule(ApiModule())
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}