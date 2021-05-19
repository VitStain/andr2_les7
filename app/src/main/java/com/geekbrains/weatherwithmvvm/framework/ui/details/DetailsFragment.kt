package com.geekbrains.weatherwithmvvm.framework.ui.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.geekbrains.weatherwithmvvm.*
import com.geekbrains.weatherwithmvvm.databinding.FragmentDetailsBinding
import com.geekbrains.weatherwithmvvm.model.AppState
import com.geekbrains.weatherwithmvvm.model.entities.Weather
import com.geekbrains.weatherwithmvvm.model.rest.rest_entities.FactDTO
import com.geekbrains.weatherwithmvvm.model.rest.rest_entities.WeatherDTO
import com.squareup.picasso.Picasso
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

@Suppress("NAME_SHADOWING")
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    private lateinit var weather: Weather

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }
    private lateinit var chosenHeaderPicture: String

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    WeatherDTO(
                        FactDTO(
                            intent.getIntExtra(
                                DETAILS_TEMP_EXTRA, TEMP_INVALID
                            ),
                            intent.getIntExtra(DETAILS_FEELS_LIKE_EXTRA, FEELS_LIKE_INVALID),
                            intent.getStringExtra(
                                DETAILS_CONDITION_EXTRA
                            )
                        )
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weather = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()

        with(binding) {
            cityName.text = weather.city.city
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weather.city.lat.toString(),
                weather.city.lon.toString()
            )
            viewModel.liveDataToObserve.observe(this@DetailsFragment, { appState ->
                when (appState) {
                    is AppState.Error -> {
                        //...
                        loadingLayout.visibility = View.GONE
                    }
                    AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE
                    is AppState.Success -> {
                        loadingLayout.visibility = View.GONE
                        temperatureValue.text = appState.weatherData[0].temperature?.toString()
                        feelsLikeValue.text = appState.weatherData[0].feelsLike?.toString()
                        weatherCondition.text = appState.weatherData[0].condition

                    }
                }
            })
            viewModel.loadData(weather.city.lat, weather.city.lon)
            loadImage("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(loadResultsReceiver)
        super.onStop()
    }

    private fun loadImage(url: String) = with(binding) {
        val city = weather.city
        cityName.text = city.city
        chosenHeaderPicture = getHeaderPicture(city.city)
        Picasso.get()
            .load(chosenHeaderPicture)
            .placeholder(R.drawable.ic_earth)
            .fit()
            .into(imageView)
    }

    private fun renderData(weatherDTO: WeatherDTO) = with(binding) {
        mainView.visibility = View.VISIBLE
        loadingLayout.visibility = View.GONE

        val fact = weatherDTO.factWeather
        val temp = fact!!.temp
        val feelsLike = fact.feelsLike
        val condition = fact.condition
        if (temp == TEMP_INVALID || feelsLike == FEELS_LIKE_INVALID || condition == null) {
            TODO(PROCESS_ERROR)
        } else {
            val city = weather.city
            cityName.text = city.city
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            temperatureValue.text = temp.toString()
            feelsLikeValue.text = feelsLike.toString()
            weatherCondition.text = condition
            weather.icon?.let {
                GlideToVectorYou.justLoadImage(
                    activity,
                    Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg"),
                    weatherIcon
                )
            }

        }
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}