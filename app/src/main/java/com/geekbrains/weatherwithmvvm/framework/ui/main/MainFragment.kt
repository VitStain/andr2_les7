package com.geekbrains.weatherwithmvvm.framework.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.weatherwithmvvm.R
import com.geekbrains.weatherwithmvvm.adapters.MainFragmentAdapter
import com.geekbrains.weatherwithmvvm.databinding.MainFragmentBinding
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractorImpl
import com.geekbrains.weatherwithmvvm.model.AppState
import com.geekbrains.weatherwithmvvm.model.entities.Weather
import com.geekbrains.weatherwithmvvm.model.interfaces.OnItemViewClickListener
import com.geekbrains.weatherwithmvvm.model.showSnackBar
import com.geekbrains.weatherwithmvvm.framework.ui.details.DetailsFragment

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var adapter: MainFragmentAdapter? = null
    private var isDataSetRus: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }

        viewModel.stringsInteractor = StringsInteractorImpl(requireContext())
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()

        /*val tryResult = try {
            ///...
            throw IndexOutOfBoundsException()
            "assa"
        } catch (exc: IndexOutOfBoundsException) {
            ///..
            "exception happened"
        } finally {
            "hello"
        }*/
    }

    private fun changeWeatherDataSet() = with(binding) {
        /*when (10 + 5) {
            5 -> println("Here is 5")
            7 -> println("Here is 7")
            else -> println("Here is something else")
        }*/

        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    @Suppress("NAME_SHADOWING")
    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(weather: Weather) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                            }
                            manager.beginTransaction()
                                    .add(R.id.container, DetailsFragment.newInstance(bundle))
                                    .addToBackStack("")
                                    .commitAllowingStateLoss()
                        }
                    }
                }
                ).apply {
                    setWeather(appState.weatherData)
                }
                mainFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                mainFragmentFAB.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        { viewModel.getWeatherFromLocalSourceRus() }
                )
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}