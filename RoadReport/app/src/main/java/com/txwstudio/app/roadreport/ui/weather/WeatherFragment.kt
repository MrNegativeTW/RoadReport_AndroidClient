package com.txwstudio.app.roadreport.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.txwstudio.app.roadreport.R
import com.txwstudio.app.roadreport.adapter.WeatherCardAdapter
import com.txwstudio.app.roadreport.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        binding.viewModel = weatherViewModel
        binding.lifecycleOwner = this

        val adapter = WeatherCardAdapter()
        binding.recyclerViewWeatherFrag.adapter = adapter
        subscribeUI(adapter)


        binding.refreshViewWeatherFrag.setOnRefreshListener {
            weatherViewModel.getWeatherStationListAndSetupWeatherCard()
        }

        val adRequest = AdRequest.Builder().build()
        binding.adViewWeatherFrag.loadAd(adRequest)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        weatherViewModel.getWeatherStationListAndSetupWeatherCard()
    }

    fun subscribeUI(adapter: WeatherCardAdapter) {
        weatherViewModel.weatherDataList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        weatherViewModel.isRefreshing.observe(viewLifecycleOwner) {
            binding.refreshViewWeatherFrag.isRefreshing = it
        }
    }
}