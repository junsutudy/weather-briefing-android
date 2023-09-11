package app.junsu.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.junsu.weather.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(WeatherUiState.initial())
    val stateFlow: StateFlow<WeatherUiState>
        get() = _stateFlow.asStateFlow()

    init {
        fetchTemperature()
    }

    private fun fetchTemperature() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateFlow.emit(_stateFlow.value.copy(temperature = weatherRepository.fetchTemperature().first()))
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
