package com.svetlanakuro.pictureoftheday.data

import com.svetlanakuro.pictureoftheday.data.entity.*
import retrofit2.Call
import retrofit2.http.*

interface NasaApiService {

    @GET("planetary/apod")
    fun getDailyImage(
        @Query("api_key")
        apiKey: String
    ): Call<DailyImageResponse>

    @GET("EPIC/api/natural/images")
    fun getEarthPhotos(
        @Query("api_key")
        apiKey: String
    ): Call<List<EarthPhotoResponse>>

    @GET("DONKI/GST")
    fun getGstInfo(
        @Query("startDate")
        startDate: String,
        @Query("endDate")
        endDate: String,
        @Query("api_key")
        key: String
    ): Call<List<GstResponse>>
}