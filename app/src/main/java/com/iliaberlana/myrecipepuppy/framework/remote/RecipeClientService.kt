package com.iliaberlana.myrecipepuppy.framework.remote

import com.iliaberlana.myrecipepuppy.BuildConfig
import com.iliaberlana.myrecipepuppy.domain.exception.DomainError
import com.iliaberlana.myrecipepuppy.framework.remote.model.RecipeRemote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RecipeClientService {
    private val URL_BASE = "http://www.recipepuppy.com"

    private val retrofitBuilder: Retrofit.Builder

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofitBuilder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())

    }

    fun createApi(baseUrl: String): RecipeClient = retrofitBuilder.baseUrl(baseUrl).build().create(RecipeClient::class.java)

    suspend fun searchRecipes(ingredients: String, page: Int): List<RecipeRemote> {
        try {
            val reponseMovieDB = createApi(URL_BASE).searchRecipes(ingredients, page)
            return reponseMovieDB.results
        } catch (error: Error) {
            throw DomainError.NoInternetConnectionException
        }
    }
}