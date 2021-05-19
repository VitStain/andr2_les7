package com.geekbrains.weatherwithmvvm.model

import com.geekbrains.weatherwithmvvm.model.entities.Weather
import com.geekbrains.weatherwithmvvm.model.rest.rest_entities.WeatherDTO
import retrofit2.Call
import retrofit2.Callback

interface Repository {
    fun getWeatherFromServerAsync(lat: Double, lng: Double, callback: Callback<WeatherDTO>)
    fun getWeatherFromServer(lat: Double, lng: Double): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}