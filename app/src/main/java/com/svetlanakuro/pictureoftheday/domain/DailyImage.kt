package com.svetlanakuro.pictureoftheday.domain

import com.svetlanakuro.pictureoftheday.data.entity.NASAImageResponse

sealed class DailyImage {

    data class Success(val serverResponseData: NASAImageResponse) : DailyImage()

    data class Error(val error: Throwable) : DailyImage()

    data class Loading(val progress: Int?) : DailyImage()

}