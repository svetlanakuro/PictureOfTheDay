package com.svetlanakuro.pictureoftheday.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaApiRetrofit {

    private val baseUrl = "https://api.nasa.gov/"

    fun getNasaService(): NasaApiService {
        val podRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        return podRetrofit.create(NasaApiService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(loggingInterceptor)
        return httpClient.build()
    }
}