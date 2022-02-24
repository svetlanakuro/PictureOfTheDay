package com.svetlanakuro.pictureoftheday.data.entity

import com.google.gson.annotations.SerializedName

data class GstResponse(
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("allKpIndex")
    val allKpIndex: List<AllKpIndex>
) {
    data class AllKpIndex(
    @SerializedName("kpIndex")
    val kpIndex: String
    )
}