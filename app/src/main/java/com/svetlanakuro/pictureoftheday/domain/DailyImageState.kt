package com.svetlanakuro.pictureoftheday.domain

import com.svetlanakuro.pictureoftheday.data.entity.DailyImageResponse

sealed class DailyImageState {

    data class Success(val dailyImageData: DailyImageResponse) : DailyImageState()

    data class Error(val error: Throwable) : DailyImageState()

}