package com.stevehechio.apps.tvshowmanager.data.api

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.test.espresso.ApolloIdlingResource
import com.stevehechio.apps.tvshowmanager.MoviesListQuery
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.annotations.NotNull
import org.junit.*
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import androidx.arch.core.executor.testing.InstantTaskExecutorRule





/**
 * Created by stevehechio on 9/5/21
 */

class TvShowApiServiceTest{

    @Rule
    val server = MockWebServer()

    private val okHttpClient = OkHttpClient()
    private var idlingResource: ApolloIdlingResource? = null
    private var apolloClient: ApolloClient? = null

    private val TIME_OUT_SECONDS: Long = 3
    private val IDLING_RESOURCE_NAME = "apolloIdlingResource"

    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

        @Before
   fun mockServer(){
        server.start()
    }

    @Throws(IOException::class)
    fun enqueueResponse(fileName: String?) {
        val inputStream = Objects.requireNonNull(TvShowApiServiceTest::class.java.classLoader)
            .getResourceAsStream(String.format("%s", fileName))
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        server.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }


    @Test
    @Throws(InterruptedException::class)
    fun checkIsIdleNow_whenCallIsQueued() {
        enqueueResponse("movie_response.json")
//        val latch = CountDownLatch(1)
        val executorService: ExecutorService = Executors.newFixedThreadPool(1)
        apolloClient = ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .dispatcher(executorService)
            .serverUrl(server.url("/"))
            .build()

        apolloClient?.query(MoviesListQuery(cursor = Input.fromNullable(null)))
            ?.enqueue(object : ApolloCall.Callback<MoviesListQuery.Data?>() {
                override fun onFailure(@NotNull e: ApolloException) {
                    throw AssertionError("This callback can't be called.")
                }

                override fun onResponse(response: Response<MoviesListQuery.Data?>) {
                  Assert.assertEquals(response.data?.movies?.pageInfo?.hasPreviousPage,false)
                }
            })
//        latch.await(TIME_OUT_SECONDS, TimeUnit.SECONDS);

    }


    @After
    fun closeServer(){
        server.shutdown()
    }

}