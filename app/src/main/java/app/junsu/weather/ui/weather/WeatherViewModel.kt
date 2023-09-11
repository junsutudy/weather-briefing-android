package app.junsu.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.junsu.weather.data.Weather
import app.junsu.weather.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(WeatherUiState.initial())
    val stateFlow: StateFlow<WeatherUiState>
        get() = _stateFlow.asStateFlow()

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateFlow.emit(
                value = _stateFlow.value.copy(
                    weather = weatherRepository.fetchWeather(),
                ),
            )
        }
    }
}

data class WeatherUiState(
    val weather: Weather? = null,
) {
    companion object {
        fun initial() = WeatherUiState()
    }
}
