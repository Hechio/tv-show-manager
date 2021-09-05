package com.stevehechio.apps.tvshowmanager.data.api

import android.database.AbstractCursor
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.apollographql.apollo.rx3.Rx3Apollo
import com.stevehechio.apps.tvshowmanager.CreateMovieMutation
import com.stevehechio.apps.tvshowmanager.MoviesListQuery
import com.stevehechio.apps.tvshowmanager.type.CreateMovieInput
import io.reactivex.rxjava3.core.Observable
import java.util.*
import javax.inject.Inject

/**
 * Created by stevehechio on 9/4/21
 */
class TvShowApiService @Inject constructor(private var apolloClient: ApolloClient){
    fun fetchMovies(cursor: String?): Observable<MoviesListQuery.Movies>{
        return Rx3Apollo.from(apolloClient.query(MoviesListQuery(cursor = Input.fromNullable(cursor)))
            .toBuilder()
            .responseFetcher(ApolloResponseFetchers.CACHE_FIRST)
            .build()).map { it.data?.movies }
    }

    fun createMovie(input: CreateMovieInput): Observable<CreateMovieMutation.CreateMovie>{
        return Rx3Apollo.from(apolloClient.mutate(CreateMovieMutation(input = input)))
            .map { it.data?.createMovie }
    }

}