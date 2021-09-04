package com.stevehechio.apps.tvshowmanager.di.components

import android.app.Application
import com.stevehechio.apps.tvshowmanager.ShowManagerApp
import com.stevehechio.apps.tvshowmanager.di.modules.ActivityModule
import com.stevehechio.apps.tvshowmanager.di.modules.ApiModule
import com.stevehechio.apps.tvshowmanager.di.modules.FragmentModule
import com.stevehechio.apps.tvshowmanager.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by stevehechio on 9/3/21
 */
@Component(modules = [ActivityModule::class,ApiModule::class,ViewModelModule::class,
    FragmentModule::class,AndroidSupportInjectionModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiModule(apiModule: ApiModule): Builder

        fun build(): AppComponent
    }

    fun inject(showManagerApp: ShowManagerApp)
}