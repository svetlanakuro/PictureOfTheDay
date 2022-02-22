package com.svetlanakuro.pictureoftheday.data.entity

import com.google.gson.annotations.SerializedName

data class EarthPhotoResponse(
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("date")
    val date: String
)