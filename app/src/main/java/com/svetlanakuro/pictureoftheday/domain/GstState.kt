package com.svetlanakuro.pictureoftheday.domain

import com.svetlanakuro.pictureoftheday.data.entity.GstResponse

sealed class GstState {

    data class Success(val gstData: List<GstResponse>) : GstState()

    data class Error(val error: Throwable) : GstState()

}