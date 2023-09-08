package app.junsu.weather

import androidx.lifecycle.ViewModel
import app.junsu.weather.data.repository.WeatherRepository

class MainActivityViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel()
