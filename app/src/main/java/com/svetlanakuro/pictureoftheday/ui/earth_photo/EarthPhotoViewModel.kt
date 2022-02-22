package com.svetlanakuro.pictureoftheday.ui.earth_photo

import android.app.Application
import androidx.lifecycle.*
import com.svetlanakuro.pictureoftheday.app.App
import com.svetlanakuro.pictureoftheday.data.entity.EarthPhotoResponse
import com.svetlanakuro.pictureoftheday.domain.*

class EarthPhotoViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var earthPhotos: List<EarthPhotoResponse>? = null

    val initViewPagerLiveData: LiveData<Event<List<EarthPhotoResponse>>> = MutableLiveData()

    fun onViewIsReady(app: Application) {
        nasaDataLoader = (app as App).nasaDataLoader
        if (earthPhotos == null) {
            loadPhotos()
        } else {
            earthPhotos?.let { initViewPagerLiveData.postValue(Event(it)) }
        }
    }

    private fun loadPhotos() {
        nasaDataLoader.loadEarthPhoto { state ->
            when (state) {
                is EarthPhotoState.Success -> {
                    initViewPagerLiveData.postValue(Event(state.earthPhotoData))
                }
                is EarthPhotoState.Error -> {
                    //todo notify about loading error
                }
            }
        }
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}