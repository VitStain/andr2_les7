package com.geekbrains.weatherwithmvvm.framework.services

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.geekbrains.weatherwithmvvm.*
import com.geekbrains.weatherwithmvvm.model.rest.rest_entities.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val LATITUDE_EXTRA = "Latitude"
const val LONGITUDE_EXTRA = "Longitude"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class DetailsService(name: String = "DetailService") : JobIntentService() {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleWork(intent: Intent) {
        val lat = intent.getDoubleExtra(LATITUDE_EXTRA, 0.0)
        val lon = intent.getDoubleExtra(LONGITUDE_EXTRA, 0.0)
        if (lat == 0.0 && lon == 0.0) {
            onEmptyData()
        } else {
            loadWeather(lat.toString(), lon.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadWeather(lat: String, lon: String) {
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty(REQUEST_API_KEY, WEATHER_API_KEY)
                }

                val weatherDTO: WeatherDTO =
                    Gson().fromJson(
                        getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                        WeatherDTO::class.java
                    )
                onResponse(weatherDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(weatherDTO: WeatherDTO) {
        val fact = weatherDTO.factWeather
        fact?.let {
            onSuccessResponse(it.temp, it.feelsLike, it.condition)
        } ?: run { onEmptyResponse() }
    }

    private fun onSuccessResponse(temp: Int?, feelsLike: Int?, condition: String?) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TEMP_EXTRA, temp)
        broadcastIntent.putExtra(DETAILS_FEELS_LIKE_EXTRA, feelsLike)
        broadcastIntent.putExtra(DETAILS_CONDITION_EXTRA, condition)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

    companion object {
        fun start(context: Context, intent: Intent) {
            enqueueWork(context, DetailsService::class.java, 3323, intent)
        }
    }
}