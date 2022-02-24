package com.svetlanakuro.pictureoftheday.domain

import com.svetlanakuro.pictureoftheday.data.entity.EarthPhotoResponse

sealed class EarthPhotoState {

    data class Success(val earthPhotoData: List<EarthPhotoResponse>) : EarthPhotoState()

    data class Error(val error: Throwable) : EarthPhotoState()

}