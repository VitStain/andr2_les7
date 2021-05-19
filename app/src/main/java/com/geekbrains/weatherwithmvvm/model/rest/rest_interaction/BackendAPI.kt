package com.geekbrains.weatherwithmvvm.model.rest.rest_interaction

import com.geekbrains.weatherwithmvvm.model.rest.rest_entities.WeatherDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BackendAPI {
    @GET("informers")
    fun getWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherDTO>

    @GET("informers")
    fun getWeatherSync(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<WeatherDTO>
}