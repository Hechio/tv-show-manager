package com.stevehechio.apps.tvshowmanager.di.modules

import com.stevehechio.apps.tvshowmanager.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by stevehechio on 9/3/21
 */
@Module
abstract class ActivityModule {
    /**
     * This allows us to inject things into Activities using AndroidInjection.inject(this)
     * in the onCreate() method.
     */
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}