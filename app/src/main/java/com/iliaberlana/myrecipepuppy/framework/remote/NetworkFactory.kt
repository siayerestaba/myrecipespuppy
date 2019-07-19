package com.iliaberlana.myrecipepuppy.framework.remote

import com.iliaberlana.myrecipepuppy.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkFactory {

    private val okHttpClient: OkHttpClient
    private val retrofitBuilder: Retrofit.Builder

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
    }

    fun <T> createApi(apiClass: Class<T>, baseUrl: String, interceptors: List<Interceptor> = emptyList()): T {
        if (interceptors.isNotEmpty()) {
            val newClient = okHttpClient.newBuilder()
            interceptors.forEach {
                newClient.addInterceptor(it)
            }
            retrofitBuilder.client(newClient.build())
        }
        return retrofitBuilder.baseUrl(baseUrl).build().create(apiClass)
    }
}