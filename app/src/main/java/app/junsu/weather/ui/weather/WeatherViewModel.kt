package app.junsu.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.junsu.weather.data.HeadlineNews
import app.junsu.weather.data.Weather
import app.junsu.weather.data.repository.NewsRepository
import app.junsu.weather.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(WeatherUiState.initial())
    val stateFlow: StateFlow<WeatherUiState>
        get() = _stateFlow.asStateFlow()

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch(Dispatchers.IO) {
            val weather = weatherRepository.fetchWeather()
            val headlineNews = newsRepository.headlineNewsFlow().first()
            _stateFlow.emit(
                value = stateFlow.value.copy(
                    weather = weather,
                    headlineNews = headlineNews,
                ),
            )
        }
    }
}

data class WeatherUiState(
    val weather: Weather?,
    val headlineNews: HeadlineNews?,
) {
    companion object {
        fun initial() = WeatherUiState(
            weather = null,
            headlineNews = null,
        )
    }
}
