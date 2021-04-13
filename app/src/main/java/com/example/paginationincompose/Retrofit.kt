package com.example.paginationincompose

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://picsum.photos/v2/"
    val client = OkHttpClient.Builder()
        .addInterceptor(
            ChuckerInterceptor.Builder(App.instance)
                .collector(ChuckerCollector(App.instance))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: PhotoApiService = getRetrofit().create(PhotoApiService::class.java)
}