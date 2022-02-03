package com.svetlanakuro.pictureoftheday.ui

import androidx.lifecycle.*
import com.svetlanakuro.pictureoftheday.BuildConfig
import com.svetlanakuro.pictureoftheday.data.NasaApiRetrofit
import com.svetlanakuro.pictureoftheday.data.entity.NASAImageResponse
import com.svetlanakuro.pictureoftheday.domain.DailyImage
import retrofit2.*

class DailyImageViewModel(
    private val liveDataForViewToObserve: MutableLiveData<DailyImage> = MutableLiveData(),
    private val retrofitImpl: NasaApiRetrofit = NasaApiRetrofit(),
) : ViewModel() {

    fun getImageData(): LiveData<DailyImage> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = DailyImage.Loading(null)

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            DailyImage.Error(Throwable("API key is invalid or missing"))
        } else {
            executeImageRequest(apiKey)
        }
    }

    private fun executeImageRequest(apiKey: String) {
        val callback = object : Callback<NASAImageResponse> {

            override fun onResponse(
                call: Call<NASAImageResponse>,
                response: Response<NASAImageResponse>,
            ) {
                handleImageResponse(response)
            }

            override fun onFailure(call: Call<NASAImageResponse>, t: Throwable) {
                liveDataForViewToObserve.value = DailyImage.Error(t)
            }
        }

        retrofitImpl.getNasaService().getImage(apiKey).enqueue(callback)
    }

    private fun handleImageResponse(response: Response<NASAImageResponse>) {
        if (response.isSuccessful && response.body() != null) {
            liveDataForViewToObserve.value = DailyImage.Success(response.body()!!)
            return
        }

        val message = response.message()
        if (message.isNullOrEmpty()) {
            liveDataForViewToObserve.value = DailyImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForViewToObserve.value = DailyImage.Error(Throwable(message))
        }
    }
}