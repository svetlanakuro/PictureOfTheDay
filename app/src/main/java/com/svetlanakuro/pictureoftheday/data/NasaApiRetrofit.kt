package com.svetlanakuro.pictureoftheday.data

import com.svetlanakuro.pictureoftheday.BuildConfig
import com.svetlanakuro.pictureoftheday.data.entity.*
import com.svetlanakuro.pictureoftheday.domain.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

private const val BASE_URL = "https://api.nasa.gov/"

class NasaApiRetrofit : NasaDataLoader {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    private val api: NasaApiService by lazy { retrofit.create(NasaApiService::class.java) }

    override fun loadDailyImage(callback: (DailyImageState) -> Unit) {
        val enqueueCallback = object : Callback<DailyImageResponse> {
            override fun onResponse(call: Call<DailyImageResponse>, response: Response<DailyImageResponse>) {
                val body = response.body()
                if (body == null) {
                    callback(DailyImageState.Error(Throwable("Error loading data from server")))
                } else {
                    callback(DailyImageState.Success(body))
                }
            }

            override fun onFailure(call: Call<DailyImageResponse>, throwable: Throwable) {
                callback(DailyImageState.Error(throwable))
            }
        }
        api.getDailyImage(BuildConfig.NASA_API_KEY).enqueue(enqueueCallback)
    }

    override fun loadEarthPhoto(callback: (EarthPhotoState) -> Unit) {
        val enqueueCallback = object : Callback<List<EarthPhotoResponse>> {
            override fun onResponse(
                call: Call<List<EarthPhotoResponse>>, response: Response<List<EarthPhotoResponse>>
            ) {
                val body = response.body()
                if (body == null) {
                    callback(EarthPhotoState.Error(Throwable("Error loading data from server")))
                } else {
                    callback(EarthPhotoState.Success(body))
                }
            }

            override fun onFailure(call: Call<List<EarthPhotoResponse>>, throwable: Throwable) {
                callback(EarthPhotoState.Error(throwable))
            }
        }
        api.getEarthPhotos(BuildConfig.NASA_API_KEY).enqueue(enqueueCallback)
    }

    override fun loadGST(callback: (GstState) -> Unit) {
        val endDate = LocalDateTime.now().toString().substring(0, 10)
        val endYear = endDate.substring(0, 4).toInt()
        val startYear = endYear - 1
        val startDate = "$startYear${endDate.substring(4)}"

        val enqueueCallback = object : Callback<List<GstResponse>> {
            override fun onResponse(
                call: Call<List<GstResponse>>, response: Response<List<GstResponse>>
            ) {
                val body = response.body()
                if (body == null) {
                    callback(GstState.Error(Throwable("Error loading data from server")))
                } else {
                    callback(GstState.Success(body))
                }
            }

            override fun onFailure(call: Call<List<GstResponse>>, throwable: Throwable) {
                callback(GstState.Error(throwable))
            }
        }
        api.getGstInfo(startDate, endDate, BuildConfig.NASA_API_KEY).enqueue(enqueueCallback)
    }
}