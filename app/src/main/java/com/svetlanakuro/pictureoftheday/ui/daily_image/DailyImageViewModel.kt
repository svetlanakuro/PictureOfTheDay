package com.svetlanakuro.pictureoftheday.ui.daily_image

import android.app.Application
import androidx.lifecycle.*
import com.svetlanakuro.pictureoftheday.app.App
import com.svetlanakuro.pictureoftheday.data.entity.DailyImageResponse
import com.svetlanakuro.pictureoftheday.domain.*

class DailyImageViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var dailyImage: DailyImageResponse? = null

    val renderImageDataLiveData: LiveData<DailyImageResponse> = MutableLiveData()

    fun onViewIsReady(app: Application) {
        nasaDataLoader = (app as App).nasaDataLoader
        if (dailyImage == null) {
            loadImage()
        } else {
            dailyImage?.apply { renderImageDataLiveData.postValue(this) }
        }
    }

    private fun loadImage() {
        nasaDataLoader.loadDailyImage { state ->
            when (state) {
                is DailyImageState.Error -> {
                    //todo notify about loading error
                }
                is DailyImageState.Success -> {
                    dailyImage = state.dailyImageData
                    renderImageDataLiveData.postValue(state.dailyImageData)
                }
            }
        }
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}