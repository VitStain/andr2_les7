package com.geekbrains.weatherwithmvvm.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("fact")
    val factWeather: FactDTO?
)