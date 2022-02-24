package com.svetlanakuro.pictureoftheday.app

import android.app.Application
import android.content.Context
import com.svetlanakuro.pictureoftheday.data.NasaApiRetrofit
import com.svetlanakuro.pictureoftheday.domain.NasaDataLoader

class App : Application() {

    val nasaDataLoader: NasaDataLoader by lazy { NasaApiRetrofit() }
}

fun Context.app(): App = applicationContext as App