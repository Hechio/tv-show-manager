package com.stevehechio.apps.tvshowmanager.data.interceptors

import android.content.Context
import com.stevehechio.apps.tvshowmanager.data.Utils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by stevehechio on 9/3/21
 */

class AuthorizationInterceptor(context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-Parse-Client-Key", Utils.clientKey)
            .addHeader("X-Parse-Application-Id", Utils.appId)
            .build()
        return chain.proceed(request)
    }

}
