package com.geekbrains.weatherwithmvvm.model.interfaces

import com.geekbrains.weatherwithmvvm.model.entities.Weather

interface OnItemViewClickListener {
    fun onItemViewClick(weather: Weather)
}