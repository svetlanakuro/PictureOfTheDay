package com.svetlanakuro.pictureoftheday.ui.gst

import android.app.Application
import androidx.lifecycle.*
import com.svetlanakuro.pictureoftheday.app.App
import com.svetlanakuro.pictureoftheday.data.entity.GstResponse
import com.svetlanakuro.pictureoftheday.domain.*

class GstViewModel : ViewModel() {

    private lateinit var nasaDataLoader: NasaDataLoader
    private var gstData: List<GstResponse>? = null

    val setGstDataLiveData: LiveData<String> = MutableLiveData()

    fun onViewCreated(application: Application) {
        nasaDataLoader = (application as App).nasaDataLoader
        val currentGstData = gstData
        if (currentGstData == null) {
            loadGstData()
        } else {
            setGstDataLiveData.postValue(generateGstListString(currentGstData))
        }
    }

    private fun loadGstData() {
        nasaDataLoader.loadGST { state ->
            when (state) {
                is GstState.Success -> {
                    gstData = state.gstData
                    setGstDataLiveData.postValue(generateGstListString(state.gstData))
                }
                is GstState.Error -> {
                    //todo notify about loading error
                }
            }
        }
    }

    private fun generateGstListString(gstData: List<GstResponse>): String {
        val stringBuilder = StringBuilder()
        gstData.reversed().forEachIndexed { index, gstItem ->
            stringBuilder.append(index + 1).append(". Start Time: ").append(gstItem.startTime)
                .append(". KpIndex: ").append(gstItem.allKpIndex.component1().kpIndex)
            if (index != gstData.size - 1) {
                stringBuilder.append("\n")
            }
        }
        return stringBuilder.toString()
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}