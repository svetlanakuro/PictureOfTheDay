package com.svetlanakuro.pictureoftheday.domain

interface NasaDataLoader {

    fun loadDailyImage(callback: (DailyImageState) -> Unit)

    fun loadEarthPhoto(callback: (EarthPhotoState) -> Unit)

    fun loadGST(callback: (GstState) -> Unit)

}