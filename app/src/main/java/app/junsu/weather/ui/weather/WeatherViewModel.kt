package app.junsu.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.junsu.weather.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {
    val flow = MutableStateFlow(WeatherUiState.initial())

    init {
        fetchTemperature()
    }

    private fun fetchTemperature() {
        viewModelScope.launch(Dispatchers.IO) {
            flow.emit(flow.value.copy(temperature = weatherRepository.fetchTemperature().first()))
        }
    }
}

data class WeatherUiState(
    val temperature: Float,
) {
    companion object {
        fun initial() = WeatherUiState(
            temperature = 0f,
        )
    }
}
