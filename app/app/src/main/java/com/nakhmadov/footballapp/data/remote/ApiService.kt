package com.nakhmadov.footballapp.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nakhmadov.footballapp.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "http://api.football-data.org/v2/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val okHttpClient = OkHttpClient.Builder().addInterceptor {
    val originalRequest = it.request()
    val request = originalRequest.newBuilder()
        .addHeader("X-Auth-Token", BuildConfig.ApiKey)
        .build()
    it.proceed(request)
}.build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

object ApiService {
    val retrofitApi: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}