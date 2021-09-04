package com.stevehechio.apps.tvshowmanager.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stevehechio.apps.tvshowmanager.ui.base.ViewModelFactory
import com.stevehechio.apps.tvshowmanager.ui.base.ViewModelKey
import com.stevehechio.apps.tvshowmanager.ui.viewmodels.AddShowViewModel
import com.stevehechio.apps.tvshowmanager.ui.viewmodels.MoviesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by stevehechio on 9/4/21
 */

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory)
    : ViewModelProvider.Factory
    /**
     * injects this object into a Map using the @IntoMap annotation,
     * with the  PaymentMethodViewModel.class as key,
     * and a Provider that will build a ViewModel
     * object.
     *
     * */

    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel::class)
    internal abstract fun bindMoviesListViewModel(moviesListViewModel: MoviesListViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddShowViewModel::class)
    internal abstract fun bindAddShowViewModel(addShowViewModel: AddShowViewModel):ViewModel
}