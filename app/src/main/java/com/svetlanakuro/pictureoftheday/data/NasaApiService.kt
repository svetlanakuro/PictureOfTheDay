package com.svetlanakuro.pictureoftheday.data

import com.svetlanakuro.pictureoftheday.data.entity.NASAImageResponse
import retrofit2.Call
import retrofit2.http.*

interface NasaApiService {

    @GET("planetary/apod")
    fun getImage(
        @Query("api_key")
        apiKey: String,
    ): Call<NASAImageResponse>
}