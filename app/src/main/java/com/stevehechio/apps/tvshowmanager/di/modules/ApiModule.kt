package com.stevehechio.apps.tvshowmanager.di.modules

import android.app.Application
import android.content.Context
import android.os.Looper
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.stevehechio.apps.tvshowmanager.data.api.TvShowApiService
import com.stevehechio.apps.tvshowmanager.data.interceptors.AuthorizationInterceptor
import com.stevehechio.apps.tvshowmanager.data.repository.AddShowRepository
import com.stevehechio.apps.tvshowmanager.data.repository.MoviesListRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by stevehechio on 9/3/21
 */

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun providesSqlNormalizeCacheFactory(application: Application): SqlNormalizedCacheFactory {
        return SqlNormalizedCacheFactory(application,"tv_show_db")
    }

    @Provides
    @Singleton
    internal fun providesLruNormalizeCacheFactory(): LruNormalizedCacheFactory {
        return LruNormalizedCacheFactory(EvictionPolicy.builder().maxSizeBytes(10485760L).build())
    }

    @Provides
    @Singleton
    internal fun providesOkHttpClient(application: Application): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(application))
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApolloClient(sqlNormalizedCacheFactory: SqlNormalizedCacheFactory,
                            lruNormalizedCacheFactory:LruNormalizedCacheFactory,
                            okHttpClient:OkHttpClient): ApolloClient {
        check(Looper.myLooper() == Looper.getMainLooper()){
            "Only the main thread can get the apolloClient instance"
        }
        return ApolloClient.builder()
            .serverUrl("https://tv-show-manager.combyne.com/graphql/")
            .normalizedCache(lruNormalizedCacheFactory.chain(sqlNormalizedCacheFactory))
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideTvShowApiService(apolloClient: ApolloClient): TvShowApiService{
        return TvShowApiService(apolloClient)
    }

    @Provides
    @Singleton
    internal fun provideMoviesListRepository(tvShowApiService: TvShowApiService): MoviesListRepository{
        return MoviesListRepository(tvShowApiService)
    }

    @Provides
    @Singleton
    internal fun provideAddShowRepository(tvShowApiService: TvShowApiService): AddShowRepository{
        return AddShowRepository(tvShowApiService)
    }


}