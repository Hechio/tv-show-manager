package com.stevehechio.apps.tvshowmanager.di.modules

import com.stevehechio.apps.tvshowmanager.ui.fragments.AddShowFragment
import com.stevehechio.apps.tvshowmanager.ui.fragments.ShowListsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by stevehechio on 9/4/21
 */
@Suppress("unused")
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeShowListFragment(): ShowListsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddShowFragment(): AddShowFragment
}