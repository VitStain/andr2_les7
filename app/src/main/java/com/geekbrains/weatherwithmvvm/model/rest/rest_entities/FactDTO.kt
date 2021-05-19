package com.geekbrains.weatherwithmvvm.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class FactDTO(
    @SerializedName("temp")
    val temp: Int?,

    @SerializedName("feels_like")
    val feelsLike: Int?,

    @SerializedName("condition")
    val condition: String?,

    @SerializedName("icon")
    val icon: String?
)
